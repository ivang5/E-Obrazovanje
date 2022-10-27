import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from '../entities/Student';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Administrator } from '../entities/Administrator';
import { Nastavnik } from '../entities/Nastavnik';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

const httpOptionsLogin = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
  responseType: 'text' as 'json',
};

@Injectable({
  providedIn: 'root',
})
export class KorisnikService {
  private apiUrl = 'http://localhost:8080/korisnici';
  helper = new JwtHelperService();

  constructor(private http: HttpClient) {}

  tryLogin(korisnickoIme: string, lozinka: string): Observable<any> {
    const url = `${this.apiUrl}/login`;
    return this.http.post(
      url,
      { korisnickoIme: korisnickoIme, lozinka: lozinka },
      httpOptionsLogin
    );
  }

  getAdministratori(): Observable<Administrator[]> {
    return this.http.get<Administrator[]>(`${this.apiUrl}/administratori`);
  }

  getNastavnici(): Observable<Nastavnik[]> {
    return this.http.get<Nastavnik[]>(`${this.apiUrl}/nastavnici`);
  }

  getStudenti(): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.apiUrl}/studenti`);
  }

  getAdministrator(korIme: string): Observable<Administrator> {
    return this.http.get<Administrator>(
      `${this.apiUrl}/administratori/${korIme}`
    );
  }

  getNastavnik(korIme: string): Observable<Nastavnik> {
    return this.http.get<Nastavnik>(`${this.apiUrl}/nastavnici/${korIme}`);
  }

  getStudent(korIme: string): Observable<Student> {
    return this.http.get<Student>(`${this.apiUrl}/studenti/${korIme}`);
  }

  postAdministrator(administrator: Administrator) {
    const url = `${this.apiUrl}/administratori`;
    return this.http.post(url, administrator, httpOptions);
  }

  postNastavnik(nastavnik: Nastavnik) {
    const url = `${this.apiUrl}/nastavnici`;
    return this.http.post(url, nastavnik, httpOptions);
  }

  postStudent(student: Student) {
    const url = `${this.apiUrl}/studenti`;
    return this.http.post(url, student, httpOptions);
  }

  putAdministrator(administrator: Administrator) {
    const url = `${this.apiUrl}/administratori`;
    return this.http.put(url, administrator, httpOptions);
  }

  putNastavnik(nastavnik: Nastavnik) {
    const url = `${this.apiUrl}/nastavnici`;
    return this.http.put(url, nastavnik, httpOptions);
  }

  putStudent(student: Student) {
    const url = `${this.apiUrl}/studenti`;
    return this.http.put(url, student, httpOptions);
  }

  deleteKorisnik(id: number) {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete(url);
  }

  getDecodedToken() {
    const token = localStorage.getItem('access_token');
    const decodedToken = token !== null ? this.helper.decodeToken(token) : null;

    return decodedToken;
  }
}
