import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StudentService } from '../../../core/services/student.service';
import { GradeService } from '../../../core/services/grade.service';
import { AttendanceService } from '../../../core/services/attendance.service';
import { NotificationService } from '../../../core/services/notification.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-detail',
  standalone: true,
  imports: [CommonModule], 
  templateUrl: './student-detail.component.html'
})
export class StudentDetailComponent implements OnInit {

  rollNumber!: number;
  student: any;

  grades: any[] = [];
  attendance: any[] = [];
  notifications: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private studentService: StudentService,
    private gradeService: GradeService,
    private attendanceService: AttendanceService,
    private notificationService: NotificationService
  ) {}

 ngOnInit() {
  this.route.paramMap.subscribe(params => {
    this.rollNumber = Number(params.get('rollNumber'));

    this.loadStudent();
    this.loadGrades();
    this.loadAttendance();
    this.loadNotifications();
  });
}

  loadStudent() {
    this.studentService.getByRoll(this.rollNumber)
      .subscribe(data => this.student = data);
  }

  loadGrades() {
  this.gradeService.getGradeByStudent(this.rollNumber)
    .subscribe((res: any) => {

      const gradeObj = res.data as { [key: string]: number };

      if (gradeObj) {
        this.grades = Object.entries(gradeObj).map(
          ([subject, score]) => ({
            subject,
            score
          })
        );
      }
    });
}

loadAttendance() {
  this.attendanceService.getAttendanceByStudent(this.rollNumber)
    .subscribe((res: any) => {

      const attendanceMap = res.data as { [key: string]: string };

      if (attendanceMap) {
        this.attendance = Object.entries(attendanceMap).map(
          ([date, status]) => ({
            date,
            status
          })
        );
      }
    });
}

loadNotifications() {
  this.notificationService.getByStudent(this.rollNumber)
    .subscribe((res: any) => {

      const notificationMap = res.data as { [key: string]: string };

      if (notificationMap) {
        this.notifications = Object.entries(notificationMap).map(
          ([timestamp, message]) => ({
            timestamp,
            message
          })
        );
      }
    });
}

goToGrades() {
  this.router.navigate(['/grades'], {
    queryParams: { rollNumber: this.rollNumber }
  });
}

goToAttendance() {
  this.router.navigate(['/attendance'], {
    queryParams: { rollNumber: this.rollNumber }
  });
}

goToNotifications() {
  this.router.navigate(['/notification'], {
    queryParams: { rollNumber: this.rollNumber }
  });
}
}