import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { Student } from 'src/app/entities/Student';
import { KorisnikService } from 'src/app/services/korisnik.service';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css'],
})
export class StudentComponent implements OnInit {
  uloga: string;
  korIme: string;
  student: Student;
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

    this.ucitajStudenta();
  }

  ucitajStudenta() {
    this.korisnikService
      .getStudent(this.korIme)
      .subscribe((student) => (this.student = student));
  }

  izmeniStudenta() {
    this.korisnikService.putStudent(this.student).subscribe();
    this.izmenaModal?.toggle();
  }

  obrisiStudenta() {
    this.korisnikService
      .deleteKorisnik(this.student.id!)
      .subscribe(() => this.router.navigate(['/studenti']));
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
