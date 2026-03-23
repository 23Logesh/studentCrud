import { Component } from '@angular/core';

@Component({
  selector: 'app-not-found',
  standalone: true,
  template: `
    <h2>404 - Page Not Found</h2>
    <a routerLink="/">Go Home</a>
  `
})
export class NotFoundComponent {}