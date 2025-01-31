import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import { UpdateUserDto } from '../../interfaces/updateUserDto.interface';
import { catchError, of } from 'rxjs';
import { UserInformation } from '../../interfaces/userInformationDto.interface';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';

@Component({
  selector: 'app-user-password',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    ReactiveFormsModule,
  ],
  templateUrl: './user-password.component.html',
  styleUrl: './user-password.component.scss',
})
export class UserPasswordComponent implements OnInit {
  passwordForm: FormGroup;
  userInfo: UserInformation | null = null;
  hideOldPassword = true;
  hideNewPassword = true;
  hideConfirmPassword = true;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.passwordForm = this.fb.group(
      {
        oldPassword: ['', [Validators.required]],
        newPassword: [
          '',
          [
            Validators.required,
            Validators.minLength(8),
            Validators.maxLength(40),
            Validators.pattern(
              //Au moins une minuscule, unemajuscule, un chiffre et un symbole
              /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]+$/
            ),
          ],
        ],
        /*confirmPassword: ['', [Validators.required]],*/
      },
      //{ validators: UserPasswordComponent.passwordsMatchValidator }
    );
  }

  ngOnInit(): void {
    this.authService.userInfo$.subscribe((userInfo) => {
      this.userInfo = userInfo;
    });
  }

  static passwordsMatchValidator(group: FormGroup): ValidationErrors | null {
    const newPassword = group.get('newPassword');
    const confirmPassword = group.get('confirmPassword');

    if (newPassword && confirmPassword) {
      const newPasswordValue = newPassword.value || '';
      const confirmPasswordValue = confirmPassword.value || '';
      if (
        confirmPasswordValue.length === newPasswordValue.length &&
        confirmPasswordValue !== newPasswordValue
      ) {
        return { passwordsMismatch: true };
      }
    }

    return null;
  }

  onSubmit(): void {
    if (this.passwordForm.valid) {
      const { oldPassword, newPassword } = this.passwordForm.value;

      this.authService
        .login({
          userIdentity: this.userInfo?.username || '',
          password: oldPassword,
        })
        .pipe(
          catchError(() => {
            this.snackBar.open('Ancien mot de passe incorrect.', '', {
              duration: 1500,
            });
            this.passwordForm.reset();
            return of(null);
          })
        )
        .subscribe((userInfo) => {
          if (userInfo) {
            const updateDto: UpdateUserDto = { password: newPassword };
            this.userService.updateUser(userInfo.id, updateDto).subscribe({
              next: () => {
                this.snackBar.open('Mot de passe mis à jour avec succès.', '', {
                  duration: 1500,
                });
                this.passwordForm.reset();
                this.passwordForm.markAsPristine();
                this.passwordForm.markAsUntouched();

                this.router.navigate(['/articles']);
              },
              error: () => {
                this.snackBar.open(
                  'Échec de la mise à jour du mot de passe.',
                  '',
                  { duration: 1500 }
                );
              },
            });
          }
        });
    }
  }
}
