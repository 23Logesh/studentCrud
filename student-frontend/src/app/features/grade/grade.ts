import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GradeService } from '../../core/services/grade.service';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2'; // ✅ Import SweetAlert

@Component({
  selector: 'app-grade',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './grade.html'
})
export class GradeComponent {

  rollNumber!: number;
  gradeList: any[] = [];

  newSubject = '';
  newScore!: number;

  constructor(private gradeService: GradeService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (params['rollNumber']) {
        this.rollNumber = +params['rollNumber'];
        this.loadGrades();
      }
    });
  }

  // ------------------ LOAD GRADES ------------------
  loadGrades() {
    if (!this.rollNumber) {
      this.showToast("Enter roll number", "warning");
      return;
    }

    this.gradeService.getGradeByStudent(this.rollNumber)
      .subscribe({
        next: (res: any) => {
          if (!res || !res.data) {
            this.showToast("No grades found", "warning");
            this.gradeList = [];
            return;
          }

          // Convert Map → Array
          this.gradeList = Object.entries(res.data).map(
            ([label, score]) => {
              const gradeId = label.split(':')[0].replace('GradeId-', '').trim();
              const subject = label.split(': ')[1].trim();
              return {
                gradeId: +gradeId,
                subject: subject,
                score: score
              };
            }
          );
        },
        error: () => this.showToast("Error loading grades ❌", "error")
      });
  }

  // ------------------ ADD GRADE ------------------
  addGrade() {
    if (!this.rollNumber || !this.newSubject || this.newScore == null) {
      this.showToast("Please fill all fields", "error");
      return;
    }

    this.gradeService.saveGrade(this.rollNumber, this.newSubject, this.newScore)
      .subscribe({
        next: () => {
          this.showToast("Grade added successfully ✅", "success");
          this.loadGrades();
          this.newSubject = '';
          this.newScore = 0;
        },
        error: (err) => {
          console.error(err);
          this.showToast("Failed to add grade ❌", "error");
        }
      });
  }

  // ------------------ DELETE GRADE ------------------
  deleteGrade(gradeId: number) {
    this.gradeService.deleteGrade(gradeId)
      .subscribe({
        next: () => {
          this.showToast("Deleted successfully 🗑", "success");
          this.loadGrades();
        },
        error: () => this.showToast("Delete failed ❌", "error")
      });
  }

  // ------------------ UPDATE SCORE ------------------
  updateGradeScore(gradeId: number, newScore: number) {
    if (newScore == null || newScore < 0) {
      this.showToast("Invalid score", "error");
      return;
    }

    this.gradeService.updateScore(gradeId, newScore)
      .subscribe({
        next: () => {
          this.showToast("Score updated successfully ✅", "success");
          this.loadGrades();
        },
        error: () => this.showToast("Failed to update score ❌", "error")
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