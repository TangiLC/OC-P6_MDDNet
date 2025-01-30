import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ThemesService } from '../../services/theme.service';
import { AuthService } from '../../services/auth.service';
import { switchMap, combineLatest } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';

@Component({
  selector: 'app-create-theme',
  standalone: true,
  templateUrl: './create-theme.component.html',
  styleUrls: ['./create-theme.component.scss'],
  imports: [
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatRadioModule,
    MatIconModule,
  ],
})
export class CreateThemeComponent implements OnInit {
  themeForm!: FormGroup;
  isAdmin: boolean = false;
  isEditMode: boolean = false;
  themeId: number | null = null;
  originalTheme: any = null;
  icons: string[] = Array.from(
    { length: 9 },
    (_, i) => `theme${String.fromCharCode(65 + i)}`
  );

  constructor(
    private fb: FormBuilder,
    private themeService: ThemesService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.authService.userInfo$.subscribe((user) => {
      this.isAdmin = user?.isAdmin || false;
    });

    this.initForm();

    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.themeId = +id;
        this.loadThemeDataIfPermitted(+id);
      }
    });
  }

  initForm(): void {
    this.themeForm = this.fb.group({
      title: ['', [Validators.required, Validators.maxLength(255)]],
      description: ['', Validators.required],
      color: ['', Validators.required],
      icon: [null],
    });
  }

  loadThemeDataIfPermitted(id: number): void {
    combineLatest([
      this.authService.userInfo$,
      this.themeService.getThemeById(id),
    ]).subscribe({
      next: ([userInfo, theme]) => {
        if (!userInfo?.isAdmin) {
          this.snackBar.open(
            '403 : You shall not pass ! (Admin only)',
            'Close',
            { duration: 3000 }
          );
          this.router.navigate(['/themes']);
          return;
        }

        this.originalTheme = theme;
        this.themeForm.patchValue({
          title: theme.title,
          description: theme.description,
          color: '#' + theme.color,
          icon: theme.icon,
        });
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement du thème', 'Close', {
          duration: 1500,
        });
        this.router.navigate(['/themes']);
      },
    });
  }

  selectIcon(icon: string): void {
    this.themeForm.patchValue({ icon });
  }

  onSubmit(): void {
    if (!this.isAdmin) {
      this.snackBar.open('403 : You shall not pass ! (Admin only)', 'Close', {
        duration: 3000,
      });
      return;
    }
    if (this.themeForm.invalid) return;

    const themeData = {
      ...this.themeForm.value,
      color: this.themeForm.value.color.replace('#', '').toUpperCase()
    };

    const request$ =
      this.isEditMode && this.themeId
        ? this.themeService.updateTheme(this.themeId, themeData)
        : this.themeService.createTheme(themeData);

    request$.pipe(switchMap(() => this.themeService.loadThemes())).subscribe({
      next: () => {
        this.snackBar.open(
          `Thème ${this.isEditMode ? 'modifié' : 'créé'} avec succès!`,
          'Close',
          { duration: 1500 }
        );
        this.router.navigate(['/themes']);
      },
      error: () => {
        this.snackBar.open("Échec de l'opération.", 'Close', {
          duration: 3000,
        });
      },
    });
  }

  deleteTheme(): void {
    if (!this.themeId) return;

    if (confirm('Êtes-vous sûr de vouloir supprimer ce thème ?')) {
      this.themeService
        .deleteTheme(this.themeId)
        .pipe(switchMap(() => this.themeService.loadThemes()))
        .subscribe({
          next: () => {
            this.snackBar.open('Thème supprimé avec succès!', 'Close', {
              duration: 1500,
            });
            this.router.navigate(['/themes']);
          },
          error: () => {
            this.snackBar.open(
              'Erreur lors de la suppression du thème.',
              'Close',
              { duration: 3000 }
            );
          },
        });
    }
  }
}
