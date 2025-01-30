import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserProfileComponent } from '../../components/user-profile/user-profile.component';
import { ThemesListComponent } from '../../components/themes-list/themes-list.component';
import { AuthService } from '../../services/auth.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-user-info',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    UserProfileComponent,
    ThemesListComponent,
  ],
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.scss']
})
export class UserInfoComponent {
  constructor(public authService: AuthService) {}
}