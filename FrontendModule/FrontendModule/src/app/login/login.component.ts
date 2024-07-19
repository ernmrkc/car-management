import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  imports: [CommonModule, FormsModule]
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService,
              private router: Router) { }

  onLogin() {
    this.authService.login({ username: this.username, password: this.password }).subscribe({
      next: (token) => {
        localStorage.setItem('jwt', token);
        this.router.navigate(['/main']);
      },
      error: (err) => {
        alert('Invalid credentials');
        console.error(err);
      }
    });
  }
}
