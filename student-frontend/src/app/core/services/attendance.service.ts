import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AttendanceService {

  private baseUrl = 'http://localhost:8090/attendance';

  constructor(private http: HttpClient) {}

  getMonthlyReport(rollNumber: number, month: number, year: number) {
    return this.http.get(
      `${this.baseUrl}/monthly-report`,
      { params: { rollNumber, month, year } }
    );
  }

  markAttendance(rollNumber: number, date: string, status: string) {
    return this.http.post(
      `${this.baseUrl}/markAttendance`,
      null,
      { params: { rollNumber, date, status } }
    );
  }

  getAttendanceByStudent(rollNumber: number): Observable<any[]> {
  return this.http.get<any[]>(
    `${this.baseUrl}/student/${rollNumber}`
  );
}

  updateStatus(id: number, status: string) {
    return this.http.patch(
      `${this.baseUrl}/update-status/${id}`,
      null,
      { params: { status } }
    );
  }

  deleteAttendance(id: number) {
    return this.http.delete(
      `${this.baseUrl}/delete/${id}`
    );
  }
}