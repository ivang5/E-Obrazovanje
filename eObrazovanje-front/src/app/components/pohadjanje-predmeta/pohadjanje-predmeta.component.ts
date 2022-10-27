import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import * as bootstrap from 'bootstrap';
import { PohadjanjePredmeta } from 'src/app/entities/PohadjanjePredmeta';
import { Predmet } from 'src/app/entities/Predmet';
import { Student } from 'src/app/entities/Student';
import { KorisnikService } from 'src/app/services/korisnik.service';
import { PohadjanjePredmetaService } from 'src/app/services/pohadjanje-predmeta.service';
import { PredmetService } from 'src/app/services/predmet.service';

@Component({
  selector: 'app-pohadjanje-predmeta',
  templateUrl: './pohadjanje-predmeta.component.html',
  styleUrls: ['./pohadjanje-predmeta.component.css']
})
export class PohadjanjePredmetaComponent implements OnInit {
  korisnickoIme: string;
  uloga : string;
  id: any;

  // objekti
  predmet: Predmet;
  pohadjanjePredmeta: PohadjanjePredmeta;
  student: Student;

  //liste za smestanje podataka  
  predmeti: Predmet[];
  pohadjanjaPredmeta: PohadjanjePredmeta[];
  studenti: Student[];

  //modal
  brisanjePohadjanjaModal: bootstrap.Modal | undefined;
  constructor(
    private _location: Location,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    public jwtHelper: JwtHelperService,
    private predmetService: PredmetService,
    private korisnikService: KorisnikService,
    private pohadjanjePredmetaService: PohadjanjePredmetaService,
    private fb : FormBuilder,
    ) { }

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
    this.ucitajPohadjanje();
    this.ucitajStudente(this.id);
  }



  // UCITAVANJA PODATAKA
  ucitajPohadjanje(){
    this.pohadjanjePredmetaService.getPohadjanje(this.id).subscribe((pohadjanje) => {
      this.pohadjanjePredmeta = pohadjanje;
      this.predmet = this.pohadjanjePredmeta.predmet!;
      console.log(this.pohadjanjePredmeta);
      console.log(this.predmet);
    });
  }
  ucitajStudente(id: number){
    this.pohadjanjePredmetaService.getStudenteZaPohadjanje(id).subscribe((studenti) =>{
      this.studenti = studenti;
      console.log(this.studenti)
    })
  }
  
  //BRISANJE I IZMENA
  obrisiPohadjanje(){
    this.predmet = this.pohadjanjePredmeta.predmet!;
    this.pohadjanjePredmetaService.deletePohadjanje(this.id)
    .subscribe(() => {
      this.brisanjePohadjanjaModal?.toggle();
      this._location.back();
    });
  }


  // MODAL
  openBrisanjeModal() {
    this.brisanjePohadjanjaModal = new bootstrap.Modal(
      document.getElementById('brisanjePohadjanjaModal')!
    );
    this.brisanjePohadjanjaModal?.show();
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
