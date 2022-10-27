import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { faYinYang } from '@fortawesome/free-solid-svg-icons';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { Student } from 'src/app/entities/Student';
import { Uplata } from 'src/app/entities/Uplata';
import { KorisnikService } from 'src/app/services/korisnik.service';
import { UplataService } from 'src/app/services/uplata.service';

@Component({
  selector: 'app-uplate',
  templateUrl: './uplate.component.html',
  styleUrls: ['./uplate.component.css']
})
export class UplateComponent implements OnInit {
  uloga : string;
  student: Student;
  uplate: Uplata[]= [];
  dropDownForm:FormGroup;
  dropdownSettings:IDropdownSettings={};
  studentiZaPrikaz : any[] = [];
  izabraniStudenti:any[] = [];
  selectedItems : any[] = [];
  selektovaniStudentId: number;

  studenti: any[] = [];

  praznaUplata : Uplata ={
    svrha: '',
    iznos: 0,
    datum: '',
    student: {
      ime: '',
      prezime: '',
      korisnickoIme: '',
      lozinka: '',
      email: '',
      adresa: '',
      grad: '',
      brojTelefona: '',
      jmbg: '',
      smer: '',
      godinaStudija: 0,
      racun: 0,
    },


  }
  
  selektovaniStudent: Student;
  selektovanaUplata : Uplata = this.praznaUplata;
  dodavanjeModal : Modal | undefined;
  izmenaModal : Modal | undefined;
  brisanjeModal : Modal | undefined;

  constructor(
    private router: Router,
    public jwtHelper: JwtHelperService,
    private uplataService : UplataService,
    private korisnikService : KorisnikService,
    private fb : FormBuilder,
  ) { }

  ngOnInit(): void {
    const decodedToken = this.korisnikService.getDecodedToken();

    decodedToken !== null
      ? (this.uloga = decodedToken.role.authority)
      : this.router.navigate(['/login']);

    if (this.isStudent()) {
      this.korisnikService
        .getStudent(decodedToken.sub)
        .subscribe((student) => {
          this.student = student
          this.ucitajUplateZaStudenta();
        });
    } else {
      this.ucitajUplate();
      this.getStudenti();
    }

    this.dropdownSettings = { 
      idField: 'item_id',
      textField: 'item_text',
      allowSearchFilter: true,
    }
    
    this.dropDownForm = this.fb.group({
      items : [this.selectedItems]
    })
  }

  ucitajUplate(){
    this.uplataService
      .getUplate()
      .subscribe((uplate) => (this.uplate = uplate));
  }

  ucitajUplateZaStudenta(){
    this.uplataService
      .getUplataByStudent(this.student.id!)
      .subscribe((uplate) => this.uplate = uplate);
  }
  
  dodajUplatu(){
    if (this.isAdministrator()) {
      const izabraniStudent = this.studenti.find(student=>student.id==this.selektovaniStudentId);
      this.selektovanaUplata.student = izabraniStudent;
    } else {
      this.selektovanaUplata.student = this.student;
    }

    const now = new Date();
    this.selektovanaUplata.datum=now.toISOString().split("T")[0];
    this.uplataService
    .postUplata(this.selektovanaUplata)
    .subscribe(() => {
      let student = this.selektovanaUplata.student;
      student.racun = student.racun + this.selektovanaUplata.iznos;
      this.korisnikService.putStudent(student).subscribe(() => console.log("Izvrsena transakcija"));

      if (this.isAdministrator()) {
        this.ucitajUplate();
      } else {
        this.ucitajUplateZaStudenta();
      }
    });
    this.dodavanjeModal?.toggle();
  }
  
  izmeniUplatu(){
    this.selektovanaUplata.student = this.izabraniStudenti[0];
    console.log(this.selektovanaUplata.student);
    this.uplataService
    .putUplata(this.selektovanaUplata)
    .subscribe(() => this.ucitajUplate());
    this.izmenaModal?.toggle();
  }
  
  obrisiUplatu(){
    this.uplataService
    .deleteUplata(this.selektovanaUplata.id!)
    .subscribe(() => this.ucitajUplate());
    this.brisanjeModal?.toggle();
  }

  getStudenti(){
    this.korisnikService.getStudenti().subscribe((studenti)=> this.studenti = studenti);
  }


  // getStudenti() : any[]{
  //   if(this.isAdministrator()){
  //     let itemsList: any[] = [];
  //     this.korisnikService.getStudenti().subscribe(studenti => {
  //       for(let i=0; i < studenti.length; i++) {
  //         itemsList.push( { 'item_id' : studenti[i].id, 'item_text': studenti[i].ime + " " + studenti[i].prezime } );
  //       }
  //       console.log("get studenti", itemsList)
  //     });
  //     return itemsList;
  //   }else {
  //     return [];
  //   }
    
  // }


  ///////////////////////////////////////////////////////////////////////

onSelectItem(item : any) {    
  this.korisnikService.getStudent(item.item_text)
    .subscribe(res => {
      this.izabraniStudenti.push(res);
    });    
}


onSelectItems() {
  this.korisnikService.getStudenti()
    .subscribe(res => {
      this.izabraniStudenti = res;
    })
}

onDeSelectItem(item: any) {
  this.izabraniStudenti = this.izabraniStudenti.filter((student) => student.korisnickoIme !== item.item_text);
}

onDeSelectItems() {
  this.izabraniStudenti = [];
}



//////////////////////////////////////////////////////////////////////////////





  openDodavanjeModal() {
    this.studentiZaPrikaz = this.studenti;
    this.selektovanaUplata = this.praznaUplata;
    this.dodavanjeModal = new bootstrap.Modal(
      document.getElementById('dodavanjeModal')!
    );
    this.dodavanjeModal?.show();
  }
  openIzmenaModal(id: number) {
    this.selectedItems = this.izabraniStudenti[0];
    this.studentiZaPrikaz = this.studenti;
    this.selektovanaUplata = this.uplate.find(
      (uplata) => uplata.id === id
    )!;
  
    this.izmenaModal = new bootstrap.Modal(
      document.getElementById('izmenaModal')!
    );
    this.izmenaModal?.show();
  }
  openBrisanjeModal(id: number) {
    this.selektovanaUplata = this.uplate.find(
      (uplata) => uplata.id === id
    )!;
  
    this.brisanjeModal = new bootstrap.Modal(
      document.getElementById('brisanjeModal')!
    );
    this.brisanjeModal?.show();
  }
  
  
  isAdministrator() {
    return this.uloga === 'ROLE_ADMINISTRATOR';
  }
  isStudent() {
    return this.uloga === 'ROLE_STUDENT';
  }


}
