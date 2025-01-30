import {
  ApplicationConfig,
  APP_INITIALIZER,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './interceptors/jwt.interceptor';
import { ThemesService } from './services/theme.service';

export function preloadThemes(
  themesService: ThemesService
): () => Promise<void> {
  return () =>
    themesService
      .loadThemes()
      .toPromise()
      .then(() => void 0);
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideAnimationsAsync(),
    provideHttpClient(withInterceptors([authInterceptor])),
    /*ThemesService,
    {
      provide: APP_INITIALIZER,
      useFactory: preloadThemes,
      deps: [ThemesService],
      multi: true,
    },*/
  ],
};
