import { AuthService } from './services/auth.service';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NavbarComponent } from './components/navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  constructor(private authService: AuthService) {}
}
