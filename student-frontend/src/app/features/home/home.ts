import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  template: `
  <div class="min-h-screen animated-bg flex items-center justify-center p-8">

    <div class="max-w-6xl w-full text-center space-y-12">

      <!-- HERO SECTION -->
      <div class="animate-fade-in-up">

        <h1 class="text-5xl font-extrabold
                   bg-gradient-to-r from-blue-600 via-purple-600 to-pink-500
                   bg-clip-text text-transparent mb-6">
          🎓 Student Management System
        </h1>

        <p class="text-gray-600 text-lg max-w-2xl mx-auto">
          Manage students, grades, attendance, and notifications 
          with a modern and powerful dashboard experience.
        </p>

        <!-- CTA BUTTONS -->
        <div class="flex justify-center gap-6 mt-8 flex-wrap">

          <a routerLink="/dashboard"
             class="pro-btn glow-blue">
            Go to Dashboard
          </a>

          <a routerLink="/grades"
             class="pro-btn glow-purple">
            Manage Grades
          </a>

        </div>

      </div>

      <!-- FEATURE CARDS -->
      <div class="grid md:grid-cols-3 gap-8 mt-16">

        <div class="glass-card hover:scale-105 transition-all duration-500">
          <div class="text-4xl mb-4">📘</div>
          <h3 class="text-lg font-semibold mb-2">Grades Tracking</h3>
          <p class="text-gray-500 text-sm">
            Add, update and monitor student performance easily.
          </p>
        </div>

        <div class="glass-card hover:scale-105 transition-all duration-500">
          <div class="text-4xl mb-4">📅</div>
          <h3 class="text-lg font-semibold mb-2">Attendance System</h3>
          <p class="text-gray-500 text-sm">
            Track daily attendance with smooth updates.
          </p>
        </div>

        <div class="glass-card hover:scale-105 transition-all duration-500">
          <div class="text-4xl mb-4">🔔</div>
          <h3 class="text-lg font-semibold mb-2">Notifications</h3>
          <p class="text-gray-500 text-sm">
            Keep students informed with instant notifications.
          </p>
        </div>

      </div>

    </div>

  </div>
  `
})
export class HomeComponent {}