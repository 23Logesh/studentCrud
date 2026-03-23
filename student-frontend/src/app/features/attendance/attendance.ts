import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AttendanceService } from '../../core/services/attendance.service';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2'; // ✅ Import SweetAlert

@Component({
  selector: 'app-attendance',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './attendance.html'
})
export class AttendanceComponent {

  rollNumber!: number;
  date!: string;
  status!: string;

  attendanceList: any[] = [];
  
  constructor(private attendanceService: AttendanceService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (params['rollNumber']) {
        this.rollNumber = +params['rollNumber'];
        this.loadAttendance();
      }
    });
  }

  // ------------------ MARK ATTENDANCE ------------------
  markAttendance() {
    if (!this.rollNumber || !this.date || !this.status) {
      this.showToast("Fill all fields", "error");
      return;
    }

    this.attendanceService.markAttendance(
      this.rollNumber,
      this.date,
      this.status
    ).subscribe({
      next: () => {
        this.showToast("Attendance marked successfully ✅", "success");
        this.loadAttendance();
      },
      error: () => {
        this.showToast("Failed to mark attendance ❌", "error");
      }
    });
  }

  // ------------------ LOAD ATTENDANCE ------------------
  loadAttendance() {
    if (!this.rollNumber) {
      this.showToast("Enter roll number", "warning");
      return;
    }

    this.attendanceService.getAttendanceByStudent(this.rollNumber)
      .subscribe({
        next: (res: any) => {

          if (!res || !res.data) {
            this.showToast("No attendance found", "warning");
            this.attendanceList = [];
            return;
          }

          // Convert Map → Array
          this.attendanceList = Object.entries(res.data).map(
            ([label, status]) => {

              const id = label.split(':')[0]
                .replace('Attendance Id-', '')
                .trim();

              const date = label.split(': ')[1];

              return {
                id: +id,
                date: date,
                status: status
              };
            }
          );
        },
        error: () => {
          this.showToast("Student not found or server error", "error");
        }
      });
  }

  // ------------------ UPDATE STATUS ------------------
  updateAttendanceStatus(id: number, newStatus: string) {
    if (!newStatus) {
      this.showToast("Select status", "error");
      return;
    }

    this.attendanceService.updateStatus(id, newStatus)
      .subscribe({
        next: () => {
          this.showToast("Attendance updated successfully ✅", "success");
          this.loadAttendance();
        },
        error: () => {
          this.showToast("Failed to update attendance ❌", "error");
        }
      });
  }

  // ------------------ DELETE ATTENDANCE ------------------
  deleteAttendance(id: number) {
    this.attendanceService.deleteAttendance(id)
      .subscribe({
        next: () => {
          this.showToast("Deleted successfully 🗑", "success");
          this.loadAttendance();
        },
        error: () => {
          this.showToast("Delete failed ❌", "error");
        }
      });
  }

  // ------------------ SWEETALERT TOAST ------------------
  private showToast(
    message: string,
    icon: 'success' | 'error' | 'warning'
  ): void {
    Swal.fire({
      toast: true,
      position: 'top-end',
      icon,
      title: message,
      showConfirmButton: false,
      timer: 1800,
      backdrop: false,
      timerProgressBar: false
    });
  }
}