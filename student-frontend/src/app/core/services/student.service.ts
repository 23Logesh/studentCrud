import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from '../../models/student.model';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private baseUrl = 'http://localhost:8090/student';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Student[]> {
      return this.http.get<Student[]>(`${this.baseUrl}/findAll`);
  }

  save(student: any) {
      return this.http.post(
        `${this.baseUrl}/save`,
        null,
        {
          params: {
            name: student.name!,
            email: student.email!,
            className: student.className!
          }
        }
      );
  }

  delete(rollNumber: number) {
      return this.http.delete(
        `${this.baseUrl}/delete/${rollNumber}`
      );
  }

  update(student: any) {
    return this.http.put(
      `${this.baseUrl}/update`,
      student
    );
  }

  getTop3(className: string) {
    return this.http.get<any[]>(
      `${this.baseUrl}/findTop3Rank`,
      { params: { className } }
    );
  }

  getByRoll(rollNumber: number): Observable<Student> {
    return this.http.get<Student>(
      `${this.baseUrl}/findByRollNumber`,
      { params: { rollNumber } }
    );
  }
}