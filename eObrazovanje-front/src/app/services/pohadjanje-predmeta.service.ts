import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PohadjanjePredmeta } from '../entities/PohadjanjePredmeta';
import { Student } from '../entities/Student';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class PohadjanjePredmetaService {
  private apiUrl = 'http://localhost:8080/pohadjanjePredmeta'

  constructor(private http : HttpClient) { }

  getPohadjanje(id: number) : Observable<PohadjanjePredmeta>{
    return this.http.get<PohadjanjePredmeta>(`${this.apiUrl}/${id}`);
  }

  getPohadjanjaPredmeta(id: number) : Observable<PohadjanjePredmeta[]>{
    return this.http.get<PohadjanjePredmeta[]>(`${this.apiUrl}/predmet/${id}`);
  }

  getPohadjanjePredmetaStudenta(id: number, korisnickoIme : string){
    return this.http.get<PohadjanjePredmeta[]>(`${this.apiUrl}/predmet/${id}/student/${korisnickoIme}`);
  }

  getStudenteZaPohadjanje(id: number) : Observable<Student[]>{
    return this.http.get<Student[]>(`${this.apiUrl}/${id}/studenti`);
  }

  postPohadjanje(pohadjanjePredmeta : PohadjanjePredmeta) : Observable<PohadjanjePredmeta>{
    const url =  `${this.apiUrl}`;
    return this.http.post<PohadjanjePredmeta>(url, pohadjanjePredmeta, httpOptions);
  }

  putPohadjanje(pohadjanje : PohadjanjePredmeta) {
    const url = `${this.apiUrl}`;
    return this.http.put(url, pohadjanje, httpOptions);
  }

  deletePohadjanje(id: number){
    const url = `${this.apiUrl}/${id}`
    return this.http.delete(url)
  }
}
