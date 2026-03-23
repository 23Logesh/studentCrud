import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private baseUrl = 'http://localhost:8090/notifications';

  constructor(private http: HttpClient) {}

  getByStudent(rollNumber: number): Observable<any[]> {
  return this.http.get<any[]>(
    `${this.baseUrl}/student/${rollNumber}`
  );
}
  update(id: number, message: string) {
    return this.http.patch(
      `${this.baseUrl}/update/${id}`,
      null,
      { params: { message: message } }
    );
  }

  delete(id: number) {
    return this.http.delete(`${this.baseUrl}/delete/${id}`);
  }

  getAll() {
    return this.http.get(`${this.baseUrl}/all`);
  }
}