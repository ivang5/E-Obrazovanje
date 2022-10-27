import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { Dokument } from 'src/app/entities/Dokument';
import { DokumentService } from 'src/app/services/dokument.service';

@Component({
  selector: 'app-dokument',
  templateUrl: './dokument.component.html',
  styleUrls: ['./dokument.component.css']
})
export class DokumentComponent implements OnInit {
  id: number;
  dokument: Dokument;
  izmenaModal: Modal | undefined;
  brisanjeModal: Modal | undefined;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dokumentService: DokumentService
  ) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      this.id = parseInt(params.get('id')!);
    });

    this.ucitajDokument();
  }

  ucitajDokument() {
    this.dokumentService
      .getDokument(this.id)
      .subscribe((dokument) => (this.dokument = dokument));
  }

  izmeniDokument() {
    this.dokumentService.putDokument(this.dokument).subscribe();
    this.izmenaModal?.toggle();
  }

  obrisiDokument() {
    this.dokumentService
      .deleteDokument(this.dokument.id!)
      .subscribe(() => this.router.navigate(['/dokumenti']));
    this.brisanjeModal?.toggle();
  }


  ///////////////////////////////////////////////////////////////////////////

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
