import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { Uplata } from 'src/app/entities/Uplata';
import { KorisnikService } from 'src/app/services/korisnik.service';
import { UplataService } from 'src/app/services/uplata.service';

@Component({
  selector: 'app-uplata',
  templateUrl: './uplata.component.html',
  styleUrls: ['./uplata.component.css']
})
export class UplataComponent implements OnInit {
  uloga:string;
  id: number;
  uplata: Uplata;
  izmenaModal: Modal | undefined;
  brisanjeModal: Modal | undefined;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private uplataService: UplataService,
    private korisnikService : KorisnikService,
  ) { }

  ngOnInit(): void {
    const decodedToken = this.korisnikService.getDecodedToken();

    decodedToken !== null
      ? (this.uloga = decodedToken.role.authority)
      : this.router.navigate(['/login']);

    this.activatedRoute.paramMap.subscribe((params) => {
      this.id = parseInt(params.get('id')!);
    });

    this.ucitajUplatu();
  }

  ucitajUplatu() {
    this.uplataService
      .getUplata(this.id)
      .subscribe((uplata) => (this.uplata = uplata));
  }

  izmeniUplatu() {
    this.uplataService.putUplata(this.uplata).subscribe();
    this.izmenaModal?.toggle();
  }

  obrisiUplatu() {
    this.uplataService
      .deleteUplata(this.uplata.id!)
      .subscribe(() => this.router.navigate(['/uplate']));
    this.brisanjeModal?.toggle();
  }



  //////////////////////////////////////////////////////////////////////////


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
