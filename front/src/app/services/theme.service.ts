import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { Theme } from '../interfaces/themeDto.interface';

@Injectable({
  providedIn: 'root',
})
export class ThemesService {
  private userApiUrl = `${environment.apiBaseUrl}api/`;

  private themesSubject = new BehaviorSubject<Theme[]>([]);
  public themes$ = this.themesSubject.asObservable();

  constructor(private httpClient: HttpClient) {}

  loadThemes(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(this.userApiUrl + 'themes').pipe(
      tap((themes) => {
        this.themesSubject.next(themes);
      })
    );
  }

  getThemeById(id: number): Observable<Theme> {
    return this.httpClient.get<Theme>(`${this.userApiUrl}theme/${id}`);
  }

  /*getAllThemes(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(this.userApiUrl + 'themes');
  }*/

  createTheme(theme: Partial<Theme>): Observable<Theme> {
    return this.httpClient.post<Theme>(this.userApiUrl + 'theme', theme);
  }

  updateTheme(id: number, theme: Partial<Theme>): Observable<Theme> {
    return this.httpClient.put<Theme>(`${this.userApiUrl}theme/${id}`, theme);
  }

  deleteTheme(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.userApiUrl}theme/${id}`);
  }
}
