import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { filter, map } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { NavigationEnd, Router } from '@angular/router';
import { shareReplay } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { BackArrowComponent } from '../back-arrow/back-arrow.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    BackArrowComponent,
  ],
})
export class NavbarComponent implements OnInit {
  isAuthenticated = false;
  currentUrl: string = '';
  isMenuOpen = false;
  isNavbarHidden = true;
  isHomePage = true;
  userPicture$: Observable<string> = this.authService.userInfo$.pipe(
    map((userInfo) =>
      userInfo?.picture
        ? `assets/profils/${userInfo.picture}.png`
        : 'assets/profils/default.png'
    )
  );

  constructor(public authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.isAuthenticated$.subscribe(
      (status) => (this.isAuthenticated = status)
    );

    this.router.events
      .pipe(
        filter(
          (event): event is NavigationEnd => event instanceof NavigationEnd
        )
      )
      .subscribe((event) => {
        this.currentUrl = event.url;
        this.isMenuOpen = false;
        this.updateNavbarVisibility();
      });

    this.updateNavbarVisibility();

    window.addEventListener('resize', () => this.updateNavbarVisibility());
  }

  private updateNavbarVisibility(): void {
    const screenWidth = window.innerWidth;

    if (this.currentUrl === '/home' || this.currentUrl === '/') {
      this.isHomePage = true;
    } else this.isHomePage = false;

    if (
      (this.currentUrl === '/auth/login' ||
        this.currentUrl === '/auth/register') &&
      screenWidth < 758
    ) {
      this.isNavbarHidden = true;
    } else {
      this.isNavbarHidden = false;
    }
    
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  navigateTo(route: string) {
    this.router.navigate([route]);
    console.log(route, ' CLICKED');
  }
}
