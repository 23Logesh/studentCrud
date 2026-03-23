import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home';
import { NotFoundComponent } from './shared/not-found/not-found';
import { StudentFormComponent } from './features/student/student-form/student-form';
import { DashboardComponent } from './features/dashboard/dashboard';  
import { GradeComponent } from './features/grade/grade';
import { AttendanceComponent } from './features/attendance/attendance';
import { NotificationComponent } from './features/notification/notification';
import { StudentDetailComponent } from './features/student/student-detail-component/student-detail.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'grades', component: GradeComponent },
  { path: 'attendance', component: AttendanceComponent },
  { path: 'notification', component: NotificationComponent },
  { path: 'students/:rollNumber', component: StudentDetailComponent },
  { path: '**', component: NotFoundComponent }
];