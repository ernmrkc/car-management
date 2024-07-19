import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

interface LoginRequest {
  username: string;
  password: string;
}

interface TokenResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'http://localhost:10150/user/login';

  constructor(private http: HttpClient) {}

  login(loginRequest: LoginRequest): Observable<string> {
    return this.http.post<TokenResponse>(this.loginUrl, loginRequest).pipe(
      map(response => response.token)
    );
  }

  getToken(): string | null {
    return localStorage.getItem('jwt');
  }
}
