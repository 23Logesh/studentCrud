import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NotificationService } from '../../core/services/notification.service';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2'; // ✅ Import SweetAlert

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './notification.html'
})
export class NotificationComponent {

  rollNumber!: number;
  notificationList: any[] = [];

  constructor(private notificationService: NotificationService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (params['rollNumber']) {
        this.rollNumber = +params['rollNumber'];
        this.loadNotifications();
      }
    });
  }

  // ------------------ LOAD NOTIFICATIONS ------------------
  loadNotifications() {
    if (!this.rollNumber) {
      this.showToast("Enter roll number", "warning");
      return;
    }

    this.notificationService.getByStudent(this.rollNumber)
      .subscribe({
        next: (res: any) => {
          if (!res || !res.data) {
            this.showToast("No notifications found", "warning");
            this.notificationList = [];
            return;
          }

          // Convert Map → Array
          this.notificationList = Object.entries(res.data).map(
            ([label, message]) => {
              const id = label.split(':')[0].replace('Notification Id-', '').trim();
              const timestamp = label.split(': ')[1].trim();
              return {
                id: +id,
                timestamp: timestamp,
                message: message
              };
            }
          );
        },
        error: () => this.showToast("Student not found ❌", "error")
      });
  }

  // ------------------ DELETE NOTIFICATION ------------------
  deleteNotification(id: number) {
    this.notificationService.delete(id)
      .subscribe({
        next: () => {
          this.showToast("Deleted successfully 🗑", "success");
          this.loadNotifications();
        },
        error: () => this.showToast("Delete failed ❌", "error")
      });
  }

  // ------------------ UPDATE NOTIFICATION ------------------
  updateNotification(id: number, newMessage: string) {
    if (!newMessage.trim()) {
      this.showToast("Message cannot be empty", "error");
      return;
    }

    this.notificationService.update(id, newMessage)
      .subscribe({
        next: () => {
          this.showToast("Updated successfully ✅", "success");
          this.loadNotifications();
        },
        error: () => this.showToast("Update failed ❌", "error")
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