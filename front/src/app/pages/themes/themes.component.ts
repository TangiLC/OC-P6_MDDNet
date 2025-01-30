import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { ThemesListComponent } from '../../components/themes-list/themes-list.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-themes-page',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ThemesListComponent,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
  ],
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesPageComponent {
  searchControl = new FormControl('');
}
