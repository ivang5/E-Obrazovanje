import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { JwtHelperService } from "@auth0/angular-jwt";
import * as bootstrap from "bootstrap";
import { Modal } from "bootstrap";
import { IDropdownSettings } from "ng-multiselect-dropdown";
import { Nastavnik } from "src/app/entities/Nastavnik";
import { PohadjanjePredmeta } from "src/app/entities/PohadjanjePredmeta";
import { Predmet } from "src/app/entities/Predmet";
import { Student } from "src/app/entities/Student";
import { KorisnikService } from "src/app/services/korisnik.service";
import { PohadjanjePredmetaService } from "src/app/services/pohadjanje-predmeta.service";
import { PredmetService } from "src/app/services/predmet.service";

@Component({
  selector: 'app-predmet',
  templateUrl: './predmet.component.html',
  styleUrls: ['./predmet.component.css']
})
export class PredmetComponent implements OnInit {
  korisnickoIme: string;
  uloga : string;
  id: any;

  // objekti
  predmet: Predmet;
  student: Student;
  nastavnik: Nastavnik;
  praznoPohadjanje: PohadjanjePredmeta = {
    startDate : '',
    endDate : '',
    studenti : [],
    predmet : undefined,
  };
  selektovanoPohadjanje: PohadjanjePredmeta = this.praznoPohadjanje;
  selektovaniStudent: any;

  //liste za smestanje podataka
  predmeti: Predmet[];
  pohadjanjaPredmeta: PohadjanjePredmeta[] = [];
  studenti: Student[] = [];
  nastavnici: Nastavnik[];
  izabraniStudenti: any[] = [];
  studentiZaPrikaz: any[] = [];

  // modali
  dodavanjePohadjanjaModal: Modal | undefined;
  izmenaModal: Modal | undefined;
  izmenaPohadjanjaModal: Modal | undefined;
  brisanjeModal: Modal | undefined;
  brisanjePohadjanjaModal: Modal | undefined;

  //ng-multidropdown
  selectedItems : any[] = [];
  dropdownSettings:IDropdownSettings={};
  dropDownForm:FormGroup;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    public jwtHelper: JwtHelperService,
    private predmetService: PredmetService,
    private korisnikService: KorisnikService,
    private pohadjanjePredmetaService: PohadjanjePredmetaService,
    private fb : FormBuilder,
  ){}

  ngOnInit(): void {
    const decodedToken = this.korisnikService.getDecodedToken();
    this.korisnickoIme = decodedToken.sub;
    decodedToken !== null
      ? (this.uloga = decodedToken.role.authority)
      : this.router.navigate(['/login']);
    
    this.activatedRoute.paramMap.subscribe((params) => {
      this.id = params.get('id');
    });

    // UCITAVANJA
    this.ucitajPredmet();
    if(this.isStudent()){
      this.ucitajPohadjanjePredmetaZaStudenta(this.id, this.korisnickoIme);
      this.ucitajStudente(this.id);
      console.log("IS STUDENT")
    }else {
      this.ucitajPohadjanjaZaPredmet(this.id);
      this.studenti = this.getStudente();
      console.log("IS not STUDENT")
    }

    //ng-multidropdown
    this.dropdownSettings = { 
      idField: 'item_id',
      textField: 'item_text',
      allowSearchFilter: true,
    }

    this.dropDownForm = this.fb.group({
      items : [this.selectedItems]
    })
  }

  // UCITAVANJE PODATAKA
  ucitajPredmet(){
    this.predmetService.getPredmet(this.id).subscribe((predmet) => {
      this.predmet = predmet;
      this.nastavnici = this.predmet.predavaci;
    });
  }

  ucitajPohadjanjaZaPredmet(id: number){
    this.pohadjanjePredmetaService.getPohadjanjaPredmeta(id).subscribe((pohadjanja =>{
      this.pohadjanjaPredmeta = pohadjanja;
    }));
  }

  ucitajPohadjanjePredmetaZaStudenta(id:number, korisnickoIme: string){
    this.pohadjanjePredmetaService.getPohadjanjePredmetaStudenta(id, korisnickoIme).subscribe((pohadjanje) =>{
      this.pohadjanjaPredmeta = pohadjanje;
      console.log("UCITAVANJE POHADJANJA PREDMETA ZA STUDENTA")
    });   
  }

  ucitajStudente(id: number){
    this.pohadjanjePredmetaService.getStudenteZaPohadjanje(id).subscribe((studenti) =>{
      this.studenti = studenti;
      console.log(this.studenti)
    })
  }

  getStudente() : any[]{
    if(this.isAdministrator()){
      let itemsList: any[] = [];
      this.korisnikService.getStudenti().subscribe(studenti => {
        for(let i=0; i < studenti.length; i++) {
          itemsList.push( { 'item_id' : studenti[i].id, 'item_text': studenti[i].korisnickoIme } );
        }
      });
      return itemsList;
    }else {
      return [];
    }
  }


  // DODAVANJE, IZMENA, BRISANJE
  dodajPohadjanje() {
    this.selektovanoPohadjanje.predmet = this.predmet;
    this.selektovanoPohadjanje.studenti = this.izabraniStudenti;
    
    this.pohadjanjePredmetaService
      .postPohadjanje(this.selektovanoPohadjanje)
      .subscribe(() => {
        this.selektovanoPohadjanje.endDate = '';
        this.selektovanoPohadjanje.startDate = '';
        this.onDeSelectItems();
        this.ucitajPohadjanjaZaPredmet(this.id);
      });

    this.dodavanjePohadjanjaModal?.toggle();
  }

  izmeniPredmet(){
    this.predmetService.putPredmet(this.predmet).subscribe(() => {
      this.ucitajPredmet();
      this.izmenaModal?.toggle();
    });
  }

  izmeniPohadjanje() {
    this.selektovanoPohadjanje.studenti = this.izabraniStudenti;

    console.log(this.selektovanoPohadjanje);
    

    this.pohadjanjePredmetaService
      .putPohadjanje(this.selektovanoPohadjanje)
      .subscribe(() => {
        this.onDeSelectItems();
        this.ucitajPohadjanjaZaPredmet(this.id);
      })

    this.izmenaPohadjanjaModal?.toggle();
  }

  obrisiPredmet(){
    this.predmetService
    .deletePredmet(this.id!)
    .subscribe(() => {
      this.router.navigate(['/predmeti']);
    });
  }

  obrisiPohadjanje(){
    this.pohadjanjePredmetaService.deletePohadjanje(this.selektovanoPohadjanje.id!)
    .subscribe(() => {
      this.ucitajPohadjanjaZaPredmet(this.id);
      this.brisanjePohadjanjaModal?.toggle();
    });
  }

  // MODALI
  openDodavanjePohadjanjaModal() {
    this.studentiZaPrikaz = this.studenti;
    console.log(this.studenti);
    
    this.dodavanjePohadjanjaModal = new bootstrap.Modal(
      document.getElementById('dodavanjePohadjanjaModal')!
    );
    this.dodavanjePohadjanjaModal?.show();
  }

  openIzmenaModal() {
    this.izmenaModal = new bootstrap.Modal(
      document.getElementById('izmenaModal')!
    );
    this.izmenaModal?.show();
  }

  openIzmenaPohadjanjaModal(id: number) {
    this.studentiZaPrikaz = this.studenti;
    this.selectedItems = this.izabraniStudenti;
    const pohadjanje = this.pohadjanjaPredmeta.find(
      (pohadjanje) => pohadjanje.id === id
    );

    if (pohadjanje) {
      this.selektovanoPohadjanje = pohadjanje
    }

    this.izmenaPohadjanjaModal = new bootstrap.Modal(
      document.getElementById('izmenaPohadjanjaModal')!
    );
    this.izmenaPohadjanjaModal?.show();
  }

  openBrisanjeModal() {
    this.brisanjeModal = new bootstrap.Modal(
      document.getElementById('brisanjeModal')!
    );
    this.brisanjeModal?.show();
  }

  openBrisanjePohadjanjaModal(id: number) {
    this.selektovanoPohadjanje = this.pohadjanjaPredmeta.find(
      (pohadjanje) => pohadjanje.id === id
    )!;

    this.brisanjePohadjanjaModal = new bootstrap.Modal(
      document.getElementById('brisanjePohadjanjaModal')!
    );
    this.brisanjePohadjanjaModal?.show();
  }

  // ON SELECT & DESELECT
  onSelectItem(item : any) {    
    this.selektovaniStudent = this.korisnikService.getStudent(item.item_text)
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

  // PROVERA ULOGA
  isAdministrator() {
    return this.uloga === 'ROLE_ADMINISTRATOR';
  }
  isStudent(){
    return this.uloga === 'ROLE_STUDENT';
  }
  isNastavnik(){
    return this.uloga === 'ROLE_NASTAVNIK';
  }
}