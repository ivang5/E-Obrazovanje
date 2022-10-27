import { Component, OnInit } from '@angular/core';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { Administrator } from 'src/app/entities/Administrator';
import { KorisnikService } from 'src/app/services/korisnik.service';

@Component({
  selector: 'app-administratori',
  templateUrl: './administratori.component.html',
  styleUrls: ['./administratori.component.css'],
})
export class AdministratoriComponent implements OnInit {
  administratori: Administrator[] = [];
  prazanAdministrator: Administrator = {
    ime: '',
    prezime: '',
    korisnickoIme: '',
    lozinka: '',
    email: '',
    adresa: '',
    grad: '',
    brojTelefona: '',
  };
  selektovaniAdministrator: Administrator = this.prazanAdministrator;
  dodavanjeModal: Modal | undefined;
  izmenaModal: Modal | undefined;
  brisanjeModal: Modal | undefined;

  constructor(private korisnikService: KorisnikService) {}

  ngOnInit(): void {
    this.ucitajAdministratore();
  }

  ucitajAdministratore() {
    this.korisnikService
      .getAdministratori()
      .subscribe((administratori) => (this.administratori = administratori));
  }

  dodajAdministratora() {
    this.korisnikService
      .postAdministrator(this.selektovaniAdministrator)
      .subscribe(() => this.ucitajAdministratore());
    this.dodavanjeModal?.toggle();
  }

  izmeniAdministratora() {
    this.korisnikService
      .putAdministrator(this.selektovaniAdministrator)
      .subscribe(() => this.ucitajAdministratore());
    this.izmenaModal?.toggle();
  }

  obrisiAdministratora() {
    this.korisnikService
      .deleteKorisnik(this.selektovaniAdministrator.id!)
      .subscribe(() => this.ucitajAdministratore());
    this.brisanjeModal?.toggle();
  }

  openDodavanjeModal() {
    this.selektovaniAdministrator = this.prazanAdministrator;
    this.dodavanjeModal = new bootstrap.Modal(
      document.getElementById('dodavanjeModal')!
    );
    this.dodavanjeModal?.show();
  }

  openIzmenaModal(id: number) {
    this.selektovaniAdministrator = this.administratori.find(
      (administrator) => administrator.id === id
    )!;

    this.izmenaModal = new bootstrap.Modal(
      document.getElementById('izmenaModal')!
    );
    this.izmenaModal?.show();
  }

  openBrisanjeModal(id: number) {
    this.selektovaniAdministrator = this.administratori.find(
      (administrator) => administrator.id === id
    )!;

    this.brisanjeModal = new bootstrap.Modal(
      document.getElementById('brisanjeModal')!
    );
    this.brisanjeModal?.show();
  }
}
