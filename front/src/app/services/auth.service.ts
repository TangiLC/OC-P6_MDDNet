import { BehaviorSubject, Observable, switchMap, tap } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequestDto.interface';
import { LoginResponse } from '../interfaces/loginResponseDto.interface';
import { UserInformation } from '../interfaces/userInformationDto.interface';
import { RegisterRequest } from '../interfaces/registerRequestDto.interface';
import { HttpClient } from '@angular/common/http';
import { UserService } from './user.service';
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = `${environment.apiBaseUrl}api/auth`;
  private tokenKey = 'authToken';

  private tokenSubject = new BehaviorSubject<string | null>(this.getToken());
  private isAuthSubject = new BehaviorSubject<boolean>(this.hasToken());
  private userInfoSubject = new BehaviorSubject<UserInformation | null>(null);

  public token$ = this.tokenSubject.asObservable();
  public isAuthenticated$ = this.isAuthSubject.asObservable();
  public userInfo$ = this.userInfoSubject.asObservable();

  constructor(
    private httpClient: HttpClient,
    private userService: UserService,
    private router: Router
  ) {
    if (this.hasToken()) {
      this.loadUserInfo();
    }
  }

  private loadUserInfo(): void {
    this.userService.getUserInfo().subscribe({
      next: (userInfo: any) => {
        this.userInfoSubject.next(userInfo);
        console.log('USER INFO:', userInfo);
      },
      error: () => {
        this.removeToken();
      },
    });
  }

  public register(registerRequest: RegisterRequest): Observable<any> {
    return this.httpClient
      .post(`${this.pathService}/register`, registerRequest)
      .pipe(
        tap((response: any) => {
          if (response.statusCode && response.statusCode !== 200) {
            throw new Error(response.message || "Erreur lors de l'inscription");
          }
        })
      );
  }

  public login(loginRequest: LoginRequest): Observable<UserInformation> {
    return this.httpClient
      .post<LoginResponse>(`${this.pathService}/login`, loginRequest)
      .pipe(
        tap((response: LoginResponse) => {
          if (response.token) {
            this.saveToken(response.token);
          }
        }),
        switchMap(() => this.userService.getUserInfo()),
        tap((userInfo: UserInformation) => {
          console.log('USER INFO:', userInfo);
          this.userInfoSubject.next(userInfo);
        })
      );
  }

  private saveToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this.tokenSubject.next(token);
    this.isAuthSubject.next(true);
  }

  public getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  public removeToken(): void {
    localStorage.removeItem(this.tokenKey);
    this.tokenSubject.next(null);
    this.isAuthSubject.next(false);
  }

  public hasToken(): boolean {
    return !!this.getToken();
  }

  public setUserInfo(userInfo: UserInformation | null): void {
    this.userInfoSubject.next(userInfo);
  }

  public updateUserInfo(update: Partial<UserInformation>): void {
    const currentUser = this.userInfoSubject.getValue();
    if (currentUser) {
      const updatedUser: UserInformation = { ...currentUser, ...update };
      this.userInfoSubject.next(updatedUser);
    }
  }

  public logout(): void {
    this.removeToken();
    this.userInfoSubject.next(null);
    this.router.navigate(['/home']);
    console.log('User has been logged out');
  }
}
