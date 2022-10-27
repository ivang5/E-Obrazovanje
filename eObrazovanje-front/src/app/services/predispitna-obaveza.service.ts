import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PredispitnaObaveza } from '../entities/PredispitnaObaveza';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};
@Injectable({
  providedIn: 'root'
})
export class PredispitnaObavezaService {

  private apiUrl = 'http://localhost:8080/predispitnaObaveza';

  constructor(private http: HttpClient) { }

  getPredispitneObaveze(): Observable<PredispitnaObaveza[]> {
    return this.http.get<PredispitnaObaveza[]>(`${this.apiUrl}`);
  }

  getNeprijavljenePredispitneObaveze(id: number): Observable<PredispitnaObaveza[]> {
    return this.http.get<PredispitnaObaveza[]>(`${this.apiUrl}/neprijavljeno/student/${id}`);
  }

  getPrijavljenePredispitneObaveze(id: number): Observable<PredispitnaObaveza[]> {
    return this.http.get<PredispitnaObaveza[]>(`${this.apiUrl}/prijavljeno/student/${id}`);
  }

  getPredispitnaObaveza(id: number): Observable<PredispitnaObaveza> {
    return this.http.get<PredispitnaObaveza>(`${this.apiUrl}/${id}`);
  }

  postPredispitnaObaveza(predispitnaObaveza: PredispitnaObaveza){
    return this.http.post(`${this.apiUrl}`, predispitnaObaveza, httpOptions);
  }

  putPredispitnaObaveza(predispitnaObaveza: PredispitnaObaveza) {
    return this.http.put(`${this.apiUrl}`, predispitnaObaveza, httpOptions);
  }

  deletePredispitnaObaveza(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
