import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { ArticleService } from '../../services/article.service';
import { ThemesService } from '../../services/theme.service';
import { AuthService } from '../../services/auth.service';
import { combineLatest, Observable, of } from 'rxjs';
import { Theme } from '../../interfaces/themeDto.interface';
import { map, switchMap } from 'rxjs/operators';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Article } from '../../interfaces/articleDto.interface';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
})
export class CreateArticleComponent implements OnInit {
  articleForm!: FormGroup;
  themes$!: Observable<Theme[]>;
  filteredThemes$!: Observable<Theme[]>;
  isEditMode: boolean = false;
  articleId: number | null = null;
  originalArticle: any = null;
  constructor(
    private fb: FormBuilder,
    private articleService: ArticleService,
    private themesService: ThemesService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.initForm();

    this.themes$ = this.themesService.themes$;

    this.filteredThemes$ = this.themes$.pipe(
      map((themes) => themes.filter((theme) => theme.id !== 1))
    );

    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.articleId = +id;
        this.loadArticleDataIfPermitted(+id);
      }
    });
  }

  initForm(): void {
    this.articleForm = this.fb.group({
      title: ['', [Validators.required, Validators.maxLength(255)]],
      content: ['', Validators.required],
      themeIds: [[], Validators.required],
    });
  }

  loadArticleDataIfPermitted(id: number): void {
    combineLatest([
      this.authService.userInfo$,
      this.articleService.getArticleById(id),
    ]).subscribe({
      next: ([userInfo, article]) => {
        if (
          !userInfo?.isAdmin &&
          article.authorUsername !== userInfo?.username
        ) {
          this.snackBar.open(
            "Vous n'avez pas les droits nécessaires pour modifier l'article",
            'Close',
            {
              duration: 3000,
              horizontalPosition: 'center',
              verticalPosition: 'top',
              panelClass: ['forbidden-snackbar'],
            }
          );
          this.router.navigate(['/articles']);
          return;
        }

        this.originalArticle = article;
        this.articleForm.patchValue({
          title: article.title,
          content: article.content,
          themeIds: article.themeIds,
        });
      },
      error: () => {
        this.snackBar.open(
          "Erreur dans la récupération de l'article",
          'Close',
          {
            duration: 1500,
          }
        );
        this.router.navigate(['/articles']);
      },
    });
  }

  onSubmit(): void {
    if (this.articleForm.invalid) return;

    const formData = this.articleForm.value;

    // Ajouter le thème 1 (News) à la liste des thèmes
    const themeIds = [...formData.themeIds];
    if (!themeIds.includes(1)) {
      themeIds.push(1);
    }

    const articleData = {
      ...formData,
      themeIds,
    };
    // Ajout author auto
    this.authService.userInfo$
      .pipe(
        switchMap((user) => {
          const payload = {
            ...articleData,
            author: this.isEditMode ? this.originalArticle.author : user?.id,
          };

          if (this.isEditMode && this.articleId) {
            return this.articleService.updateArticle(this.articleId, payload);
          } else {
            return this.articleService.createArticle(payload);
          }
        }),
        switchMap(() => this.articleService.cleanupNewsArticles()),
        switchMap(() => this.themesService.loadThemes())
      )
      .subscribe({
        next: () => {
          this.snackBar.open(
            `Article ${this.isEditMode ? 'modifié' : 'créé'} avec succès!`,
            'Close',
            { duration: 1500 }
          );

          this.router.navigate(['/articles']);
        },
        error: (e) => {
          console.log('erreur creation', e);
          this.snackBar.open("Echec dans la création de l'article.", 'Close', {
            duration: 3000,
          });
        },
      });
  }

  compareThemes(t1: number, t2: number): boolean {
    return t1 === t2;
  }

  deleteArticle(): void {
    if (!this.articleId) return;

    if (confirm('Êtes-vous sûr de vouloir supprimer cet article ?')) {
      this.articleService
        .deleteArticle(this.articleId)
        .pipe(
          switchMap(() => this.articleService.cleanupNewsArticles()),
          switchMap(() => this.themesService.loadThemes())
        )
        .subscribe({
          next: () => {
            this.snackBar.open('Article supprimé avec succès!', 'Close', {
              duration: 1500,
            });

            this.router.navigate(['/articles']);
          },
          error: () => {
            this.snackBar.open(
              "Erreur lors de la suppression de l'article.",
              'Close',
              {
                duration: 3000,
              }
            );
          },
        });
    }
  }
}
