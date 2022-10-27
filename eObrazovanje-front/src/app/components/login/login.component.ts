import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KorisnikService } from 'src/app/services/korisnik.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  korIme: string;
  lozinka: string;

  constructor(
    private korisnikService: KorisnikService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  login() {
    this.korisnikService
      .tryLogin(this.korIme, this.lozinka)
      .subscribe((response) => {
        const accessToken = response.toString();
        localStorage.setItem('access_token', accessToken);
        this.router.navigate(['/']);
      });
  }
}
