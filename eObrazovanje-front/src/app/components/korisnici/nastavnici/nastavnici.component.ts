import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { Nastavnik } from 'src/app/entities/Nastavnik';
import { KorisnikService } from 'src/app/services/korisnik.service';

@Component({
  selector: 'app-nastavnici',
  templateUrl: './nastavnici.component.html',
  styleUrls: ['./nastavnici.component.css'],
})
export class NastavniciComponent implements OnInit {
  uloga: string;
  nastavnici: Nastavnik[] = [];
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
  selektovaniNastavnik: Nastavnik = this.prazanNastavnik;
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

    this.ucitajNastavnike();
  }

  ucitajNastavnike() {
    this.korisnikService
      .getNastavnici()
      .subscribe((nastavnici) => (this.nastavnici = nastavnici));
  }

  dodajNastavnika() {
    this.korisnikService
      .postNastavnik(this.selektovaniNastavnik)
      .subscribe(() => this.ucitajNastavnike());
    this.dodavanjeModal?.toggle();
  }

  izmeniNastavnika() {
    this.korisnikService
      .putNastavnik(this.selektovaniNastavnik)
      .subscribe(() => this.ucitajNastavnike());
    this.izmenaModal?.toggle();
  }

  obrisiNastavnika() {
    this.korisnikService
      .deleteKorisnik(this.selektovaniNastavnik.id!)
      .subscribe(() => this.ucitajNastavnike());
    this.brisanjeModal?.toggle();
  }

  openDodavanjeModal() {
    this.selektovaniNastavnik = this.prazanNastavnik;
    this.dodavanjeModal = new bootstrap.Modal(
      document.getElementById('dodavanjeModal')!
    );
    this.dodavanjeModal?.show();
  }

  openIzmenaModal(id: number) {
    this.selektovaniNastavnik = this.nastavnici.find(
      (nastavnik) => nastavnik.id === id
    )!;

    this.izmenaModal = new bootstrap.Modal(
      document.getElementById('izmenaModal')!
    );
    this.izmenaModal?.show();
  }

  openBrisanjeModal(id: number) {
    this.selektovaniNastavnik = this.nastavnici.find(
      (nastavnik) => nastavnik.id === id
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
