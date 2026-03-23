import {
  Component,
  EventEmitter,
  Input,
  Output,
  ChangeDetectionStrategy,
  OnChanges,
  SimpleChanges
} from '@angular/core';

import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  Validators,
  FormGroup
} from '@angular/forms';

import { StudentService } from '../../../core/services/student.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-student-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './student-form.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StudentFormComponent implements OnChanges {

  @Input() studentData: any = null;
  @Output() formClosed = new EventEmitter<boolean>();

  studentForm!: FormGroup;
  isEditMode = false;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private studentService: StudentService
  ) {
    this.studentForm = this.fb.group({
      rollNumber: [0],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      className: ['', Validators.required]
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['studentData']) {
      if (this.studentData) {
        this.isEditMode = true;
        this.studentForm.patchValue(this.studentData);
      } else {
        this.isEditMode = false;
        this.studentForm.reset();
      }
    }
  }

  onSubmit(): void {
    if (this.studentForm.invalid || this.isLoading) {
      this.showToast('Please fill all required fields', 'error');
      return;
    }

    this.isLoading = true;

    const student = this.studentForm.value;

    const request$ = this.isEditMode
      ? this.studentService.update(student)
      : this.studentService.save(student);

    request$.subscribe({
      next: () => {
        this.showToast(
          this.isEditMode
            ? 'Student updated successfully 🎉'
            : 'Student saved successfully 🚀',
          'success'
        );

        this.isLoading = false;
        this.formClosed.emit(true);
      },
      error: () => {
        this.showToast('Something went wrong ❌', 'error');
        this.isLoading = false;
      }
    });
  }

  close(): void {
    this.formClosed.emit(false);
  }

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