import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { PredispitnaObaveza } from 'src/app/entities/PredispitnaObaveza';
import { Predmet } from 'src/app/entities/Predmet';
import { Prijava } from 'src/app/entities/Prijava';
import { Student } from 'src/app/entities/Student';
import { KorisnikService } from 'src/app/services/korisnik.service';
import { PredispitnaObavezaService } from 'src/app/services/predispitna-obaveza.service';
import { PredmetService } from 'src/app/services/predmet.service';
import { PrijavaService } from 'src/app/services/prijava.service';

@Component({
  selector: 'app-predispitne-obaveze',
  templateUrl: './predispitne-obaveze.component.html',
  styleUrls: ['./predispitne-obaveze.component.css']
})
export class PredispitneObavezeComponent implements OnInit {
  uloga: string;
  predObaveze: PredispitnaObaveza[] = [];
  prijavljenePredObaveze: PredispitnaObaveza[] = [];
  predmeti: Predmet[] = [];
  student: Student;
  praznaPredObaveza: PredispitnaObaveza = {
    tipPredispitneObaveze: '',
    datum: '',
    sala: '',
    predmet: null,
  };
  selektovanaPredObaveza: PredispitnaObaveza = this.praznaPredObaveza;
  selektovaniPredmetId: number;
  dodavanjeModal: Modal | undefined;
  izmenaModal: Modal | undefined;
  brisanjeModal: Modal | undefined;
  prijavaModal: Modal | undefined;

  constructor(
    private router: Router,
    private predispitnaObavezaService: PredispitnaObavezaService, 
    private korisnikService: KorisnikService,
    private predmetService: PredmetService,
    private prijavaService: PrijavaService
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
          this.student = student;
          this.ucitajNeprijavljenePredObaveze(student.id!);
          this.ucitajPrijavljenePredObaveze(student.id!);
        });
    } else {
      this.ucitajPredObaveze();
      this.ucitajPredmete();
    }
  }

  ucitajPredObaveze() {
    this.predispitnaObavezaService
      .getPredispitneObaveze()
      .subscribe((predObaveze) => (this.predObaveze = predObaveze));
  }

  ucitajNeprijavljenePredObaveze(id: number) {
    this.predispitnaObavezaService
      .getNeprijavljenePredispitneObaveze(id)
      .subscribe((predObaveze) => (this.predObaveze = predObaveze));
  }

  ucitajPrijavljenePredObaveze(id: number) {
    this.predispitnaObavezaService
      .getPrijavljenePredispitneObaveze(id)
      .subscribe((predObaveze) => (this.prijavljenePredObaveze = predObaveze));
  }

  ucitajPredmete() {
    this.predmetService
      .getPredmeti()
      .subscribe((predmeti) => {
        this.predmeti = predmeti
        console.log(this.predmeti);
      });
  }

  dodajPredObavezu() {
    const izabraniPredmet = this.predmeti.find(predmet => predmet.id == this.selektovaniPredmetId);
    this.selektovanaPredObaveza.predmet = izabraniPredmet!;
    this.predispitnaObavezaService
      .postPredispitnaObaveza(this.selektovanaPredObaveza)
      .subscribe(() => this.ucitajPredObaveze());
    this.dodavanjeModal?.toggle();
  }

  izmeniPredObavezu() {
    this.predispitnaObavezaService
      .putPredispitnaObaveza(this.selektovanaPredObaveza)
      .subscribe(() => this.ucitajPredObaveze());
    this.izmenaModal?.toggle();
  }

  obrisiPredObavezu() {
    this.predispitnaObavezaService
      .deletePredispitnaObaveza(this.selektovanaPredObaveza.id!)
      .subscribe(() => this.ucitajPredObaveze());
    this.brisanjeModal?.toggle();
  }

  prijaviPredObavezu() {
    const prijava: Prijava = {
      ocenjen: false,
      polozen: false,
      bodovi: 0,
      predispitnaObaveza: this.selektovanaPredObaveza,
      student: this.student
    }
    console.log(prijava);
    
    this.prijavaService.postPrijava(prijava).subscribe(() => {
      if(this.selektovanaPredObaveza.tipPredispitneObaveze == "ISPIT") {
        let std = this.student;
        std.racun = std.racun - 300;
        this.korisnikService.putStudent(std).subscribe(() => console.log("Izvrsena transakcija"));
      }

      this.ucitajNeprijavljenePredObaveze(this.student.id!);
      this.ucitajPrijavljenePredObaveze(this.student.id!);
    });
    this.prijavaModal?.toggle();
  }

  openDodavanjeModal() {
    this.selektovanaPredObaveza = this.praznaPredObaveza;
    this.dodavanjeModal = new bootstrap.Modal(
      document.getElementById('dodavanjeModal')!
    );
    this.dodavanjeModal?.show();
  }

  openIzmenaModal(id: number) {
    this.selektovanaPredObaveza = this.predObaveze.find(
      (predObaveza) => predObaveza.id === id
    )!;

    this.izmenaModal = new bootstrap.Modal(
      document.getElementById('izmenaModal')!
    );
    this.izmenaModal?.show();
  }

  openBrisanjeModal(id: number) {
    this.selektovanaPredObaveza = this.predObaveze.find(
      (predObaveza) => predObaveza.id === id
    )!;

    this.brisanjeModal = new bootstrap.Modal(
      document.getElementById('brisanjeModal')!
    );
    this.brisanjeModal?.show();
  }

  openPrijavaModal(id: number) {
    this.selektovanaPredObaveza = this.predObaveze.find(
      (predObaveza) => predObaveza.id === id
    )!;

    this.prijavaModal = new bootstrap.Modal(
      document.getElementById('prijavaModal')!
    );
    this.prijavaModal?.show();
  }

  isAdministrator() {
    return this.uloga === 'ROLE_ADMINISTRATOR';
  }

  isStudent() {
    return this.uloga === 'ROLE_STUDENT';
  }
}
