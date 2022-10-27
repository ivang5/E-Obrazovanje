import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { Dokument } from 'src/app/entities/Dokument';
import { Student } from 'src/app/entities/Student';
import { DokumentService } from 'src/app/services/dokument.service';
import { KorisnikService } from 'src/app/services/korisnik.service';
import { StudentComponent } from '../korisnici/student/student.component';

@Component({
  selector: 'app-dokumenti',
  templateUrl: './dokumenti.component.html',
  styleUrls: ['./dokumenti.component.css']
})
export class DokumentiComponent implements OnInit {
  uloga : string;
  dokumenti : Dokument[] = [];
  dropDownForm:FormGroup;
  dropdownSettings:IDropdownSettings={};
  studentiZaPrikaz : any[] = [];
  izabraniStudenti:any[] = [];
  selectedItems : any[] = [];
  selektovaniStudentId: number;
  korisnickoIme: string;
  
  studenti: any[] = [];
  
  prazanDokument : Dokument = {
    nazivDokumenta: '',
    tipDokumenta: '',
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
  };

  selektovaniStudent: Student;
  selektovaniDokument : Dokument = this.prazanDokument;
  dodavanjeModal : Modal | undefined;
  izmenaModal : Modal | undefined;
  brisanjeModal : Modal | undefined;
  
 

  constructor(private router: Router,
    public jwtHelper: JwtHelperService,
    private dokumentService : DokumentService,
    private fb : FormBuilder,
    private korisnikService : KorisnikService,) { }

  ngOnInit(): void {
    const decodedToken = this.korisnikService.getDecodedToken();

    decodedToken !== null
      ? (this.uloga = decodedToken.role.authority)
      : this.router.navigate(['/login']);

      
      this.korisnickoIme= decodedToken.sub;
      this.ucitajDokumente();
      this.studenti = this.getStudenti();
      this.dropdownSettings = { 
        singleSelection:true,
        idField: 'item_id',
        textField: 'item_text',
        allowSearchFilter: true,
      }
      
      this.dropDownForm = this.fb.group({
        items : [this.selectedItems]
      })

      
  }




ucitajDokumente(){

  if (this.isStudent()) {
    console.log("student", this.dokumenti)
      this.dokumentService
      .getDokumentByStudent(this.korisnickoIme)
      .subscribe((dokumenti) => this.dokumenti = dokumenti); 
      console.log("student", this.dokumenti);
  }else if(this.isAdministrator()){
    this.dokumentService
    .getDokuments()
    .subscribe((dokumenti) => (this.dokumenti = dokumenti));
  }

}

dodajDokument(){
  this.selektovaniDokument.student = this.izabraniStudenti[0];
  this.dokumentService
  .postDokument(this.selektovaniDokument)
  .subscribe(() => this.ucitajDokumente());
  this.dodavanjeModal?.toggle();
}

izmeniDokument(){
  this.selektovaniDokument.student = this.izabraniStudenti[0];
  this.dokumentService
  .putDokument(this.selektovaniDokument)
  .subscribe(() => this.ucitajDokumente());
  this.izmenaModal?.toggle();
}

obrisiDokument(){
  this.dokumentService
  .deleteDokument(this.selektovaniDokument.id!)
  .subscribe(() => this.ucitajDokumente());
  this.brisanjeModal?.toggle();
}





getStudenti() : any[]{
  if(this.isAdministrator()){
    let itemsList: any[] = [];
    this.korisnikService.getStudenti().subscribe(studenti => {
      for(let i=0; i < studenti.length; i++) {
        itemsList.push( { 'item_id' : studenti[i].id, 'item_text': studenti[i].korisnickoIme} );
      }
      console.log("get studenti ", itemsList)
    });
    return itemsList;
  }else {
    return [];
  }
  
}


///////////////////////////////////////////////////////////////////////

onSelectItem(item : any) {    
  this.korisnikService.getStudent(item.item_text)
    .subscribe(res => {
      this.izabraniStudenti = [res];
    });    
}


// onSelectItems() {
//   this.korisnikService.getStudenti()
//     .subscribe(res => {
//       this.izabraniStudenti = res;
//     })
// }

onDeSelectItem() {
  this.izabraniStudenti = [];
}

// onDeSelectItems() {
//   this.izabraniStudenti = [];
// }



//////////////////////////////////////////////////////////////////////////////



openDodavanjeModal() {
  this.studentiZaPrikaz = this.studenti;
  this.selektovaniDokument = this.prazanDokument;
  this.dodavanjeModal = new bootstrap.Modal(
    document.getElementById('dodavanjeModal')!
  );
  this.dodavanjeModal?.show();
}
openIzmenaModal(id: number) {
  this.selectedItems = this.izabraniStudenti[0];
  this.studentiZaPrikaz = this.studenti;
  this.selektovaniDokument = this.dokumenti.find(
    (dokument) => dokument.id === id
  )!;

  this.izmenaModal = new bootstrap.Modal(
    document.getElementById('izmenaModal')!
  );
  this.izmenaModal?.show();
}
openBrisanjeModal(id: number) {
  this.selektovaniDokument = this.dokumenti.find(
    (dokument) => dokument.id === id
  )!;

  this.brisanjeModal = new bootstrap.Modal(
    document.getElementById('brisanjeModal')!
  );
  this.brisanjeModal?.show();
}


isAdministrator() {
  return this.uloga === 'ROLE_ADMINISTRATOR';
}

isStudent(){
  return this.uloga === 'ROLE_STUDENT';
}



}
