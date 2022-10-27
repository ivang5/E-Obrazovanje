import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as bootstrap from 'bootstrap';
import { Modal } from 'bootstrap';
import { Nastavnik } from 'src/app/entities/Nastavnik';
import { Predmet } from 'src/app/entities/Predmet';
import { Prijava } from 'src/app/entities/Prijava';
import { Student } from 'src/app/entities/Student';
import { KorisnikService } from 'src/app/services/korisnik.service';
import { PredmetService } from 'src/app/services/predmet.service';
import { PrijavaService } from 'src/app/services/prijava.service';

@Component({
  selector: 'app-prijave',
  templateUrl: './prijave.component.html',
  styleUrls: ['./prijave.component.css']
})
export class PrijaveComponent implements OnInit {
  uloga: string;
  prijave: Prijava[] = [];
  selektovanaPrijava: Prijava = {
    ocenjen: false,
    polozen: false,
    bodovi: 0,
    predispitnaObaveza: null,
    student: null
  };
  nastavnik: Nastavnik;
  student: Student;
  predmetiNastavnik: Predmet[] = [];
  izmenaModal: Modal | undefined;

  constructor(
    private router: Router,
    private prijavaService: PrijavaService,
    private korisnikService: KorisnikService,
  ) { }

  ngOnInit(): void {
    const decodedToken = this.korisnikService.getDecodedToken();

    decodedToken !== null
      ? (this.uloga = decodedToken.role.authority)
      : this.router.navigate(['/login']);

    if (this.isNastavnik()) {
      this.korisnikService
        .getNastavnik(decodedToken.sub)
        .subscribe((nastavnik) => {
          this.nastavnik = nastavnik;
          this.ucitajPrijaveNastavnik(nastavnik.id!)
        });
    } else if(this.isStudent()) {
      this.korisnikService
        .getStudent(decodedToken.sub)
        .subscribe((student) => {
          this.student = student;
          this.ucitajPrijaveStudent(student.id!);
        });
    } else {
      this.ucitajPrijave();
    }
  }

  ucitajPrijave() {
    this.prijavaService
      .getPrijave()
      .subscribe((prijave) => this.prijave = prijave);
  }

  ucitajPrijaveNastavnik(id: number) {
    this.prijavaService
      .getPrijaveNastavnik(id)
      .subscribe((prijave) => this.prijave = prijave);
  }

  ucitajPrijaveStudent(id: number) {
    this.prijavaService
      .getPrijaveStudent(id)
      .subscribe((prijave) => this.prijave = prijave);
  }

  oceni() {
    this.selektovanaPrijava.ocenjen = true;
    this.prijavaService
      .putPrijava(this.selektovanaPrijava)
      .subscribe(() => this.ucitajPrijaveNastavnik(this.nastavnik.id!));
    this.izmenaModal?.toggle();
  }

  openIzmenaModal(id: number) {
    this.selektovanaPrijava = this.prijave.find(
      (prijava) => prijava.id === id
    )!;

    this.izmenaModal = new bootstrap.Modal(
      document.getElementById('izmenaModal')!
    );
    this.izmenaModal?.show();
  }

  isDone(datum: string) {
    const today = new Date();
    const predObavezaDate = new Date(datum);
    return today.getTime() > predObavezaDate.getTime();
  }

  isNastavnik() {
    return this.uloga === 'ROLE_NASTAVNIK';
  }

  isStudent() {
    return this.uloga === 'ROLE_STUDENT';
  }

}
