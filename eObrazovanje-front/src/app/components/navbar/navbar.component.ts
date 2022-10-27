import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import * as bootstrap from 'bootstrap';
import { Dropdown } from 'bootstrap';
import { KorisnikService } from 'src/app/services/korisnik.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  korisnickoIme: string;
  uloga: string;
  korisniciDropdown: Dropdown | undefined;
  profilDropdown: Dropdown | undefined;

  constructor(
    private korisnikService: KorisnikService,
    private router: Router,
    public jwtHelper: JwtHelperService
  ) {}

  ngOnInit(): void {
    const decodedToken = this.korisnikService.getDecodedToken();

    decodedToken !== null
      ? ((this.korisnickoIme = decodedToken.sub),
        (this.uloga = decodedToken.role.authority))
      : this.router.navigate(['/login']);

    if (this.jwtHelper.isTokenExpired()) {
      this.logout();
    }

    this.profilDropdown = new bootstrap.Dropdown(
      document.getElementById('profilMenuLink')!
    );
  }

  ngAfterViewInit(): void {
    if (
      this.uloga === 'ROLE_ADMINISTRATOR' ||
      this.uloga === 'ROLE_NASTAVNIK'
    ) {
      this.korisniciDropdown = new bootstrap.Dropdown(
        document.getElementById('korisniciMenuLink')!
      );
    }
  }

  toggleKorisniciDropdown() {
    this.korisniciDropdown?.toggle();
  }

  toggleProfilDropdown() {
    this.profilDropdown?.toggle();
  }

  hasRole(role: string) {
    return role === this.uloga;
  }

  logout() {
    localStorage.removeItem('access_token');
    this.router.navigate(['/login']);
  }
}
