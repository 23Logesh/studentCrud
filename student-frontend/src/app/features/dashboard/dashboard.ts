import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StudentService } from '../../core/services/student.service';
import { Router } from '@angular/router';
import { StudentFormComponent } from '../student/student-form/student-form';
import Swal from 'sweetalert2'; // ✅ Import Swal here

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, StudentFormComponent],
  templateUrl: './dashboard.html'
})
export class DashboardComponent implements OnInit {

  totalStudents = 0;
  selectedClass!: string;

  classes: string[] = [];
  allStudents: any[] = [];
  filteredStudents: any[] = [];
  topStudents: any[] = [];

  searchText: string = '';

  currentPage: number = 1;
  pageSize: number = 5;
  totalPages: number = 0;
  pageSizes: number[] = [5, 10, 20];

  sortColumn: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  // 🔥 MODAL CONTROL
  showForm = false;
  selectedStudent: any = null;

  // 🔥 COLUMN VISIBILITY
  showGpa = true;
  showRank = true;
  showAttendance = true;

  // 🔥 DELETE CONFIRMATION
  showDeleteModal = false;
  rollToDelete: number | null = null;

  constructor(
    private studentService: StudentService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadStudents();
  }

  loadStudents() {
    this.studentService.getAll().subscribe(data => {
      this.allStudents = data;
      this.totalStudents = data.length;

      const classSet = new Set(data.map((s: any) => s.className));
      this.classes = Array.from(classSet);

      if (this.classes.length > 0) {
        this.selectedClass = this.classes[0];
        this.applyFilter();
      }
    });
  }

  // ------------------ ADD / EDIT ------------------

  openAddStudent() {
    this.selectedStudent = null;
    this.showForm = true;
  }

  editStudent(student: any) {
    this.selectedStudent = student;
    this.showForm = true;
  }

  handleFormClose(refresh: boolean) {
    this.showForm = false;
    this.selectedStudent = null;

    if (refresh) {
      this.loadStudents();
    }
  }

  // ------------------ FILTER ------------------

  applyFilter() {
    this.filteredStudents = this.allStudents
      .filter(s => s.className === this.selectedClass)
      .filter(student =>
        student.name.toLowerCase().includes(this.searchText.toLowerCase()) ||
        student.email.toLowerCase().includes(this.searchText.toLowerCase())
      );

    this.applySorting();
    this.currentPage = 1;
    this.calculatePagination();
    this.loadTop3();
  }

  onClassChange() {
    this.applyFilter();
  }

  // ------------------ SORTING ------------------

  sortBy(column: string) {
    if (this.sortColumn === column) {
      this.sortDirection =
        this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }
    this.applySorting();
  }

  applySorting() {
    if (!this.sortColumn) return;

    this.filteredStudents.sort((a, b) => {
      const valueA = a[this.sortColumn];
      const valueB = b[this.sortColumn];

      if (typeof valueA === 'number') {
        return this.sortDirection === 'asc'
          ? valueA - valueB
          : valueB - valueA;
      }

      return this.sortDirection === 'asc'
        ? valueA?.toString().localeCompare(valueB?.toString())
        : valueB?.toString().localeCompare(valueA?.toString());
    });
  }

  // ------------------ PAGINATION ------------------

  calculatePagination() {
    this.totalPages = Math.ceil(
      this.filteredStudents.length / this.pageSize
    );
  }

  changePageSize() {
    this.currentPage = 1;
    this.calculatePagination();
  }

  get paginatedStudents() {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.filteredStudents.slice(start, start + this.pageSize);
  }

  get pages(): number[] {
    return Array(this.totalPages)
      .fill(0)
      .map((_, i) => i + 1);
  }

  goToPage(page: number) {
    this.currentPage = page;
  }

  nextPage() {
    if (this.currentPage < this.totalPages)
      this.currentPage++;
  }

  prevPage() {
    if (this.currentPage > 1)
      this.currentPage--;
  }

  // ------------------ DELETE WITH CONFIRMATION ------------------

  confirmDelete(rollNumber: number) {
    this.rollToDelete = rollNumber;
    this.showDeleteModal = true;
  }

  cancelDelete() {
    this.rollToDelete = null;
    this.showDeleteModal = false;
  }

  deleteConfirmed() {
    if (this.rollToDelete !== null) {
      this.studentService.delete(this.rollToDelete)
        .subscribe(() => {
          this.showDeleteModal = false;
          this.rollToDelete = null;
          this.loadStudents();
          this.showToast('Student deleted successfully', 'success'); // Toast on delete
        });
    }
  }

  // ------------------ TOP 3 ------------------

  loadTop3() {
    this.studentService.getTop3(this.selectedClass)
      .subscribe(data => this.topStudents = data);
  }

  goToStudent(rollNumber: number) {
    this.router.navigate(['/students', rollNumber]);
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