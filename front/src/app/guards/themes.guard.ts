// themes.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { Observable, of, switchMap } from 'rxjs';
import { ThemesService } from '../services/theme.service';

@Injectable({
  providedIn: 'root',
})
export class ThemesGuard implements CanActivate {
  constructor(private themesService: ThemesService) {}

  canActivate(): Observable<boolean> {
    return this.themesService.themes$.pipe(
      switchMap((themes) => {
        if (themes.length) {
          return of(true);
        }
        return this.themesService.loadThemes().pipe(switchMap(() => of(true)));
      })
    );
  }
}
