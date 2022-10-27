import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { Nastavnik } from 'src/app/entities/Nastavnik';
import { KorisnikService } from 'src/app/services/korisnik.service';

@Component({
  selector: 'app-nastavnik',
  templateUrl: './nastavnik.component.html',
  styleUrls: ['./nastavnik.component.css'],
})
export class NastavnikComponent implements OnInit {
  uloga: string;
  korIme: string;
  nastavnik: Nastavnik;
  izmenaModal: Modal | undefined;
  brisanjeModal: Modal | undefined;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    public jwtHelper: JwtHelperService,
    private korisnikService: KorisnikService
  ) {}

  ngOnInit(): void {
    const decodedToken = this.korisnikService.getDecodedToken();

    decodedToken !== null
      ? (this.uloga = decodedToken.role.authority)
      : this.router.navigate(['/login']);

    this.activatedRoute.paramMap.subscribe((params) => {
      this.korIme = params.get('korisnickoIme')!;
    });

    this.ucitajNastavnika();
  }

  ucitajNastavnika() {
    this.korisnikService
      .getNastavnik(this.korIme)
      .subscribe((nastavnik) => (this.nastavnik = nastavnik));
  }

  izmeniNastavnika() {
    this.korisnikService.putNastavnik(this.nastavnik).subscribe();
    this.izmenaModal?.toggle();
  }

  obrisiNastavnika() {
    this.korisnikService
      .deleteKorisnik(this.nastavnik.id!)
      .subscribe(() => this.router.navigate(['/nastavnici']));
    this.brisanjeModal?.toggle();
  }

  openIzmenaModal() {
    this.izmenaModal = new bootstrap.Modal(
      document.getElementById('izmenaModal')!
    );
    this.izmenaModal?.show();
  }

  openBrisanjeModal() {
    this.brisanjeModal = new bootstrap.Modal(
      document.getElementById('brisanjeModal')!
    );
    this.brisanjeModal?.show();
  }

  isAdministrator() {
    return this.uloga === 'ROLE_ADMINISTRATOR';
  }
}
