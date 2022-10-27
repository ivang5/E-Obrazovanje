import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prijava } from '../entities/Prijava';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class PrijavaService {

  private apiUrl = 'http://localhost:8080/prijave';

  constructor(private http: HttpClient) {}

  getPrijave(): Observable<Prijava[]> {
    return this.http.get<Prijava[]>(this.apiUrl);
  }

  getPrijaveStudent(id: number): Observable<Prijava[]> {
    return this.http.get<Prijava[]>(`${this.apiUrl}/student/${id}`);
  }

  getPrijaveNastavnik(id: number): Observable<Prijava[]> {
    return this.http.get<Prijava[]>(`${this.apiUrl}/nastavnik/${id}`);
  }

  getPrijava(id: number): Observable<Prijava> {
    return this.http.get<Prijava>(
      `${this.apiUrl}/${id}`
    );
  }

  postPrijava(prijava: Prijava) {
    return this.http.post(this.apiUrl, prijava, httpOptions);
  }

  putPrijava(prijava: Prijava) {
    return this.http.put(this.apiUrl, prijava, httpOptions);
  }

  deletePrijava(id: number) {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete(url);
  }
}
