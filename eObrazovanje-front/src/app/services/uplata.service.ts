import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Uplata } from '../entities/Uplata';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};
@Injectable({
  providedIn: 'root'
})
export class UplataService {

  private apiUrl = 'http://localhost:8080/uplata';

  constructor(private http: HttpClient) { }

  getUplate(): Observable<Uplata[]> {
    return this.http.get<Uplata[]>(`${this.apiUrl}`);
  }

  getUplata(id: number): Observable<Uplata> {
    return this.http.get<Uplata>(`${this.apiUrl}/${id}`);
  }


  getUplataByStudent(id : number){
    const url = `${this.apiUrl}/student/${id}`;
    return this.http.get<Uplata[]>(url);
  }




  postUplata(uplata: Uplata){
    return this.http.post(`${this.apiUrl}`, uplata, httpOptions);
  }

  putUplata(uplata: Uplata) {
    return this.http.put(`${this.apiUrl}`, uplata, httpOptions);
  }

  deleteUplata(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
