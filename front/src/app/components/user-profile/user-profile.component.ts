import { MatRadioModule } from '@angular/material/radio';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormsModule,
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  ValidatorFn,
  AbstractControl,
} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';
import { UserInformation } from '../../interfaces/userInformationDto.interface';
import { UpdateUserDto } from '../../interfaces/updateUserDto.interface';
import { Router } from '@angular/router';

export function notBlankValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const value = control.value;
    const isValid =
      value !== null && value !== undefined && value.trim() !== '';
    return isValid ? null : { blank: { value: value } };
  };
}

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatRadioModule,
  ],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss',
})
export class UserProfileComponent implements OnInit {
  userInfo: UserInformation | null = null;
  profileForm: FormGroup;
  avatars: string[] = Array.from({ length: 18 }, (_, i) => `profil${i + 1}`);

  constructor(
    private userService: UserService,
    public authService: AuthService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.profileForm = this.fb.group({
      username: [''],
      email: [''],
      picture: null,
    });
  }

  ngOnInit(): void {
    this.authService.userInfo$.subscribe((userInfo) => {
      if (userInfo) {
        this.userInfo = userInfo;
        this.profileForm.patchValue({
          username: userInfo.username,
          email: userInfo.email,
          picture: userInfo.picture || 'default',
        });
      }
    });
  }

  selectAvatar(avatar: string): void {
    this.profileForm.patchValue({ picture: avatar });
  }

  onSubmit(): void {
    if (this.profileForm.valid && this.userInfo) {
      const formValues = this.profileForm.value;
      const updateDto: UpdateUserDto = {
        username:
          formValues.username !== this.userInfo.username
            ? formValues.username
            : null,
        email:
          formValues.email !== this.userInfo.email ? formValues.email : null,
        picture:
          formValues.picture !== this.userInfo.picture
            ? formValues.picture
            : null,
      };

      if (
        updateDto.username !== null ||
        updateDto.email !== null ||
        updateDto.picture !== null
      ) {
        this.userService.updateUser(this.userInfo.id, updateDto).subscribe({
          next: () => {
            const noNullUpdate: Partial<UserInformation> = Object.fromEntries(
              Object.entries(updateDto).filter(([_, v]) => v !== null)
            );
            this.authService.updateUserInfo(noNullUpdate);

            this.snackBar.open('Profil modifié avec succès !', '', {
              duration: 1500,
            });
          },
          error: () => {
            this.snackBar.open('Échec de la mise à jour du profil.', '', {
              duration: 1500,
            });
          },
        });
      }
    }
  }

  navigateToPasswordEdit(): void {
    this.router.navigate(['/profil/password']);
  }
}
