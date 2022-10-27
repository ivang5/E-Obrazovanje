import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dokument } from '../entities/Dokument';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};
@Injectable({
  providedIn: 'root'
})
export class DokumentService {
  private apiUrl = 'http://localhost:8080/dokument';

  constructor(private http: HttpClient) { }

  getDokuments(): Observable<Dokument[]> {
    return this.http.get<Dokument[]>(`${this.apiUrl}`);
  }

  getDokument(id: number): Observable<Dokument> {
    return this.http.get<Dokument>(`${this.apiUrl}/${id}`);
  }

  getDokumentByStudent(korisnickoIme: string){
    const url = `${this.apiUrl}/student/${korisnickoIme}`;
    return this.http.get<Dokument[]>(url);
  }



  postDokument(dokument: Dokument){
    return this.http.post(`${this.apiUrl}`, dokument, httpOptions);
  }

  putDokument(dokument: Dokument) {
    return this.http.put(`${this.apiUrl}`, dokument, httpOptions);
  }

  deleteDokument(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }


}
