import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Predmet } from '../entities/Predmet';
import { JwtHelperService } from '@auth0/angular-jwt';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};


@Injectable({
  providedIn: 'root',
})
export class PredmetService {
  private apiUrl = 'http://localhost:8080/predmeti'

  constructor( private http : HttpClient) { }

  getPredmeti(): Observable<Predmet[]> {
    return this.http.get<Predmet[]>(`${this.apiUrl}`);
  }

  getPredmet(id: number): Observable<Predmet> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Predmet>(url);
  }

  getPredmetStudenta(korisnickoIme : string){
    const url = `${this.apiUrl}/student/${korisnickoIme}`;
    return this.http.get<Predmet[]>(url);
  }

  getPredmetPredavaca(korisnickoIme : string){
    const url = `${this.apiUrl}/nastavnik/${korisnickoIme}`;
    return this.http.get<Predmet[]>(url);
  }

  postPredmet(predmet : Predmet): Observable<Predmet> {
    const url = `${this.apiUrl}`;
    return this.http.post<Predmet>(url, predmet, httpOptions);
  }

  putPredmet(predmet : Predmet) {
    const url = `${this.apiUrl}`;
    return this.http.put(url, predmet, httpOptions);
  }

  deletePredmet(id : number) {
    const url = `${this.apiUrl}/${id}`
    return this.http.delete(url)
  }
}
