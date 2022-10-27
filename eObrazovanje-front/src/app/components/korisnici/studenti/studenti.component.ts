import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { Student } from 'src/app/entities/Student';
import { KorisnikService } from 'src/app/services/korisnik.service';

@Component({
  selector: 'app-studenti',
  templateUrl: './studenti.component.html',
  styleUrls: ['./studenti.component.css'],
})
export class StudentiComponent implements OnInit {
  uloga: string;
  studenti: Student[] = [];
  prazanStudent: Student = {
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
    godinaStudija: 1,
    racun: 0,
  };
  selektovaniStudent: Student = this.prazanStudent;
  dodavanjeModal: Modal | undefined;
  izmenaModal: Modal | undefined;
  brisanjeModal: Modal | undefined;

  constructor(
    private router: Router,
    public jwtHelper: JwtHelperService,
    private korisnikService: KorisnikService
  ) {}

  ngOnInit(): void {
    const decodedToken = this.korisnikService.getDecodedToken();

    decodedToken !== null
      ? (this.uloga = decodedToken.role.authority)
      : this.router.navigate(['/login']);

    this.ucitajStudente();
  }

  ucitajStudente() {
    this.korisnikService
      .getStudenti()
      .subscribe((studenti) => (this.studenti = studenti));
  }

  dodajStudenta() {
    this.korisnikService
      .postStudent(this.selektovaniStudent)
      .subscribe(() => this.ucitajStudente());
    this.dodavanjeModal?.toggle();
  }

  izmeniStudenta() {
    this.korisnikService
      .putStudent(this.selektovaniStudent)
      .subscribe(() => this.ucitajStudente());
    this.izmenaModal?.toggle();
  }

  obrisiStudenta() {
    this.korisnikService
      .deleteKorisnik(this.selektovaniStudent.id!)
      .subscribe(() => this.ucitajStudente());
    this.brisanjeModal?.toggle();
  }

  openDodavanjeModal() {
    this.selektovaniStudent = this.prazanStudent;
    this.dodavanjeModal = new bootstrap.Modal(
      document.getElementById('dodavanjeModal')!
    );
    this.dodavanjeModal?.show();
  }

  openIzmenaModal(id: number) {
    this.selektovaniStudent = this.studenti.find(
      (student) => student.id === id
    )!;

    this.izmenaModal = new bootstrap.Modal(
      document.getElementById('izmenaModal')!
    );
    this.izmenaModal?.show();
  }

  openBrisanjeModal(id: number) {
    this.selektovaniStudent = this.studenti.find(
      (student) => student.id === id
    )!;

    this.brisanjeModal = new bootstrap.Modal(
      document.getElementById('brisanjeModal')!
    );
    this.brisanjeModal?.show();
  }

  isAdministrator() {
    return this.uloga === 'ROLE_ADMINISTRATOR';
  }
}
