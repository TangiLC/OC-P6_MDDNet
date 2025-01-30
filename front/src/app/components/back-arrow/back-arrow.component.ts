import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  standalone: true,
  selector: 'app-back-arrow',
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './back-arrow.component.html',
  styleUrls: ['./back-arrow.component.scss'],
})
export class BackArrowComponent {
  constructor(private location: Location, private router: Router) {}

  goBack(): void {
    if (window.history.length > 1) {
      this.location.back();
    } else {
      this.router.navigate(['/home']);
    }
  }
}
