import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { KorisnikService } from 'src/app/services/korisnik.service';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.css'],
})
export class ProfilComponent implements OnInit {
  korIme: string;
  uloga: string;
  ulogaFormatted: string;
  korisnik: any;
  izmenaModal: Modal | undefined;

  constructor(
    private router: Router,
    public jwtHelper: JwtHelperService,
    private korisnikService: KorisnikService
  ) {}

  ngOnInit(): void {
    const decodedToken = this.korisnikService.getDecodedToken();

    decodedToken !== null
      ? ((this.korIme = decodedToken.sub),
        (this.uloga = decodedToken.role.authority))
      : this.router.navigate(['/login']);

    this.ucitajKorisnika();
  }

  ucitajKorisnika() {
    if (this.uloga === 'ROLE_ADMINISTRATOR') {
      this.ulogaFormatted = 'Administrator';
      return this.korisnikService
        .getAdministrator(this.korIme)
        .subscribe((korisnik) => (this.korisnik = korisnik));
    }

    if (this.uloga === 'ROLE_NASTAVNIK') {
      this.ulogaFormatted = 'Nastavnik';
      return this.korisnikService
        .getNastavnik(this.korIme)
        .subscribe((korisnik) => (this.korisnik = korisnik));
    }

    this.ulogaFormatted = 'Student';
    return this.korisnikService
      .getStudent(this.korIme)
      .subscribe((korisnik) => (this.korisnik = korisnik));
  }

  izmeniKorisnika() {
    if (this.uloga === 'ROLE_ADMINISTRATOR') {
      this.korisnikService.putAdministrator(this.korisnik).subscribe();
    } else if (this.uloga === 'ROLE_NASTAVNIK') {
      this.korisnikService.putNastavnik(this.korisnik).subscribe();
    } else {
      this.korisnikService.putStudent(this.korisnik).subscribe();
    }

    this.izmenaModal?.toggle();
  }

  openIzmenaModal() {
    this.izmenaModal = new bootstrap.Modal(
      document.getElementById('izmenaModal')!
    );
    this.izmenaModal?.show();
  }

  isAdministrator() {
    return this.uloga === 'ROLE_ADMINISTRATOR';
  }

  isNastavnik() {
    return this.uloga === 'ROLE_NASTAVNIK';
  }

  isStudent() {
    return this.uloga === 'ROLE_STUDENT';
  }
}
