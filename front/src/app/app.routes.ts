import { Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { ThemesGuard } from './guards/themes.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    loadComponent: () =>
      import('./pages/home/home.component').then((m) => m.HomeComponent),
    canActivate: [UnauthGuard],
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./pages/auth/auth.routes').then((m) => m.routes),
    canActivate: [UnauthGuard],
  },
  {
    path: 'profil',
    loadComponent: () =>
      import('./pages/user-info/user-info.component').then(
        (m) => m.UserInfoComponent
      ),
    canActivate: [AuthGuard, ThemesGuard],
  },
  {
    path: 'profil/password',
    loadComponent: () =>
      import('./components/user-password/user-password.component').then(
        (m) => m.UserPasswordComponent
      ),
    canActivate: [AuthGuard, ThemesGuard],
  },
  {
    path: 'article/:id',
    loadComponent: () =>
      import('./pages/article/article.component').then(
        (m) => m.ArticleComponent
      ),
    canActivate: [AuthGuard, ThemesGuard],
  },
  {
    path: 'theme/:id',
    loadComponent: () =>
      import('./pages/theme/theme.component').then((m) => m.ThemeComponent),
    canActivate: [AuthGuard, ThemesGuard],
  },
  {
    path: 'themes',
    loadComponent: () =>
      import('./pages/themes/themes.component').then(
        (m) => m.ThemesPageComponent
      ),
    canActivate: [AuthGuard, ThemesGuard],
  },
  {
    path: 'articles',
    loadComponent: () =>
      import('./pages/feed/feed.component').then((m) => m.FeedComponent),
    canActivate: [AuthGuard, ThemesGuard],
  },
  {
    path: 'create_article',
    loadComponent: () =>
      import('./pages/create-article/create-article.component').then(
        (m) => m.CreateArticleComponent
      ),
    canActivate: [AuthGuard, ThemesGuard],
  },
  {
    path: 'update_article/:id',
    loadComponent: () =>
      import('./pages/create-article/create-article.component').then(
        (m) => m.CreateArticleComponent
      ),
    canActivate: [AuthGuard, ThemesGuard],
  },

  {
    path: 'create_theme',
    loadComponent: () =>
      import('./pages/create-theme/create-theme.component').then(
        (m) => m.CreateThemeComponent
      ),
    canActivate: [AuthGuard, ThemesGuard],
  },
  {
    path: 'update_theme/:id',
    loadComponent: () =>
      import('./pages/create-theme/create-theme.component').then(
        (m) => m.CreateThemeComponent
      ),
    canActivate: [AuthGuard, ThemesGuard],
  },

  {
    path: '**',
    component: NotFoundComponent,
  },
];
