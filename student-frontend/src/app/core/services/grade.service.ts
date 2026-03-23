import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class GradeService {
  private baseUrl = 'http://localhost:8090/grade';

  constructor(private http: HttpClient) {}

  getGradeByStudent(rollNumber: number) {
    return this.http.get(
      `${this.baseUrl}/student/${rollNumber}`
    );
  }
  saveGrade(rollNumber: number, subject: string, score: number) {
  return this.http.post(
    `${this.baseUrl}/save`,
    null,
    { params: { rollNumber, subject, score } }
  );
}

deleteGrade(gradeId: number) {
  return this.http.delete(`${this.baseUrl}/delete/${gradeId}`);
}

updateScore(gradeId: number, score: number) {
  return this.http.patch(
    `${this.baseUrl}/update/score`,
    null,
    {
      params: {
        gradeId: gradeId,
        score: score
      }
    }
  );
}
}
