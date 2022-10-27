import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { Administrator } from 'src/app/entities/Administrator';
import { KorisnikService } from 'src/app/services/korisnik.service';

@Component({
  selector: 'app-administrator',
  templateUrl: './administrator.component.html',
  styleUrls: ['./administrator.component.css'],
})
export class AdministratorComponent implements OnInit {
  korIme: string;
  administrator: Administrator;
  izmenaModal: Modal | undefined;
  brisanjeModal: Modal | undefined;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private korisnikService: KorisnikService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      this.korIme = params.get('korisnickoIme')!;
    });

    this.ucitajAdministratora();
  }

  ucitajAdministratora() {
    this.korisnikService
      .getAdministrator(this.korIme)
      .subscribe((administrator) => (this.administrator = administrator));
  }

  izmeniAdministratora() {
    this.korisnikService.putAdministrator(this.administrator).subscribe();
    this.izmenaModal?.toggle();
  }

  obrisiAdministratora() {
    this.korisnikService
      .deleteKorisnik(this.administrator.id!)
      .subscribe(() => this.router.navigate(['/administratori']));
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
}
