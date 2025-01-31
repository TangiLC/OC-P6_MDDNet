import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { UpdateUserDto } from '../interfaces/updateUserDto.interface';
import { UserInformation } from '../interfaces/userInformationDto.interface';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pathService = `${environment.apiBaseUrl}api/user`;
  private meService = `${environment.apiBaseUrl}api/me`;

  constructor(private httpClient: HttpClient) {}

  getUserInfo(): Observable<UserInformation> {
    return this.httpClient.get<UserInformation>(this.meService);
  }

  updateUser(id: number, updateUserDto: UpdateUserDto): Observable<any> {
    return this.httpClient.put(`${this.pathService}/${id}`, updateUserDto);
  }

  addThemeToUser(id: number): Observable<any> {
    return this.httpClient.put(`${this.pathService}/add_theme/${id}`, {});
  }

  removeThemeFromUser(id: number): Observable<any> {
    return this.httpClient.put(`${this.pathService}/remove_theme/${id}`, {});
  }
}
