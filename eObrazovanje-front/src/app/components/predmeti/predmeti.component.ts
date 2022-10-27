import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { Nastavnik } from 'src/app/entities/Nastavnik';
import { Predmet } from 'src/app/entities/Predmet';
import { KorisnikService } from 'src/app/services/korisnik.service';
import { PredmetService } from 'src/app/services/predmet.service';
import { IDropdownSettings, } from 'ng-multiselect-dropdown';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PohadjanjePredmeta } from 'src/app/entities/PohadjanjePredmeta';

@Component({
  selector: 'app-predmeti',
  templateUrl: './predmeti.component.html',
  styleUrls: ['./predmeti.component.css']
})
export class PredmetiComponent implements OnInit {
  korisnickoIme : string;
  uloga : string;
  predmeti : Predmet[] = [];
  
  prazanPredmet : Predmet = {
    naziv : '',
    espb : 0,
    predavaci : [],
  };
  selektovaniPredmet : Predmet = this.prazanPredmet;
  dodavanjeModal : Modal | undefined;
  izmenaModal : Modal | undefined;
  brisanjeModal : Modal | undefined;

  pohadjanjaPredmeta : PohadjanjePredmeta[];
  praznoPohadjanjePredmeta : PohadjanjePredmeta = {
    id : 0,
    startDate : '',
    endDate : '',
    studenti : [],
    predmet : this.prazanPredmet,
  }
  selektovanoPohadjanjePredmeta : PohadjanjePredmeta = this.praznoPohadjanjePredmeta;
  
  nastavnici : any[] = [];
  nastavniciZaPrikaz : any[] = [];
  selectedItems : any[] = [];
  dropdownSettings:IDropdownSettings={};
  dropDownForm:FormGroup;

  prazanNastavnik: Nastavnik = {
    ime: '',
    prezime: '',
    korisnickoIme: '',
    lozinka: '',
    email: '',
    adresa: '',
    grad: '',
    brojTelefona: '',
    katedra: '',
    uloga: 'PROFESOR',
  };
  selektovaniNastavnik: any;
  izabraniNastavnici: any[] = [];

  constructor(
    private router: Router,
    public jwtHelper: JwtHelperService,
    private predmetService: PredmetService,
    private korisnikService : KorisnikService,
    private fb : FormBuilder,
  ) {}

  ngOnInit(): void {
    const decodedToken = this.korisnikService.getDecodedToken();
    this.korisnickoIme = decodedToken.sub;
    decodedToken !== null
      ? (this.uloga = decodedToken.role.authority)
      : this.router.navigate(['/login']);

    this.ucitajPredmete();
    this.nastavnici = this.getNastavnike();
    this.dropdownSettings = { 
      idField: 'item_id',
      textField: 'item_text',
      allowSearchFilter: true,
    }
    
    this.dropDownForm = this.fb.group({
      items : [this.selectedItems]
    })
  }

  ucitajPredmete(){
    if (this.isStudent()) {
      this.predmetService
      .getPredmetStudenta(this.korisnickoIme)
      .subscribe((predmeti) => this.predmeti = predmeti);
    } else if (this.isNastavnik()) {
      this.predmetService
      .getPredmetPredavaca(this.korisnickoIme)
      .subscribe((predmeti) => this.predmeti = predmeti);
    }else if(this.isAdministrator()){
      this.predmetService
      .getPredmeti()
      .subscribe((predmeti) => (this.predmeti = predmeti));
    }
  }

  dodajPredmet(){
    let predmetZaDodavanje = this.selektovaniPredmet;
    predmetZaDodavanje.predavaci = []

    this.predmetService
    .postPredmet(predmetZaDodavanje)
    .subscribe((predmet) => {
      this.selektovaniPredmet.id = predmet.id;
      this.selektovaniPredmet.predavaci = this.izabraniNastavnici;
      this.predmetService
      .putPredmet(this.selektovaniPredmet)
      .subscribe(() => {
        this.selektovaniPredmet.id = null;
        this.selektovaniPredmet.espb = 0;
        this.selektovaniPredmet.naziv = '';
        this.onDeSelectItems();
        this.ucitajPredmete();
      });
    });
    
    this.dodavanjeModal?.toggle();
  }

  izmeniPredmet(){
    this.selektovaniPredmet.predavaci = this.izabraniNastavnici;
    this.predmetService
    .putPredmet(this.selektovaniPredmet)
    .subscribe(() => {
      this.onDeSelectItems();
      this.ucitajPredmete()
    });
    this.izmenaModal?.toggle();
  }

  obrisiPredmet(){
    this.predmetService
    .deletePredmet(this.selektovaniPredmet.id!)
    .subscribe(() => this.ucitajPredmete());
    this.brisanjeModal?.toggle();
  }

  getNastavnike() : any[]{
    if(this.isAdministrator()){
      let itemsList: any[] = [];
      this.korisnikService.getNastavnici().subscribe(nastavnici => {
        for(let i=0; i < nastavnici.length; i++) {
          itemsList.push( { 'item_id' : nastavnici[i].id, 'item_text':  nastavnici[i].korisnickoIme } );
        }
        console.log("getnastavnike ", itemsList)
      });
      return itemsList;
    }else {
      return [];
    }
    
  }

  onSelectItem(item : any) {    
    this.selektovaniNastavnik = this.korisnikService.getNastavnik(item.item_text)
      .subscribe(res => {
        this.izabraniNastavnici.push(res);
      });    
  }

  onSelectItems() {
    this.korisnikService.getNastavnici()
      .subscribe(res => {
        this.izabraniNastavnici = res;
      })
  }

  onDeSelectItem(item: any) {
    this.izabraniNastavnici = this.izabraniNastavnici.filter((nastavnik) => nastavnik.korisnickoIme !== item.item_text);
  }

  onDeSelectItems() {
    this.izabraniNastavnici = [];
  }

  openDodavanjeModal() {
    this.nastavniciZaPrikaz = this.nastavnici;
    this.selektovaniPredmet = this.prazanPredmet;
    this.dodavanjeModal = new bootstrap.Modal(
      document.getElementById('dodavanjeModal')!
    );
    this.dodavanjeModal?.show();
  }
  openIzmenaModal(id: number) {
    this.nastavniciZaPrikaz = this.nastavnici;
    this.selectedItems = this.izabraniNastavnici;
    this.selektovaniPredmet = this.predmeti.find(
      (predmet) => predmet.id === id
    )!;

    this.izmenaModal = new bootstrap.Modal(
      document.getElementById('izmenaModal')!
    );
    this.izmenaModal?.show();
  }
  openBrisanjeModal(id: number) {
    this.selektovaniPredmet = this.predmeti.find(
      (predmet) => predmet.id === id
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
  isNastavnik(){
    return this.uloga === 'ROLE_NASTAVNIK';
  }
}
