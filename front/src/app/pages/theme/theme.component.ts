import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, Router } from '@angular/router';
import {
  Observable,
  forkJoin,
  map,
  of,
  switchMap,
  timeout,
  timeoutWith,
} from 'rxjs';
import { ThemesService } from '../../services/theme.service';
import { ArticleService } from '../../services/article.service';
import { Theme } from '../../interfaces/themeDto.interface';
import { Article } from '../../interfaces/articleDto.interface';
import { ArticleDetailComponent } from '../../components/article-detail/article-detail.component';
import { MatButtonModule } from '@angular/material/button';
import { SortFormComponent } from '../../components/sort-form/sort-form.component';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    ArticleDetailComponent,
    SortFormComponent,
  ],
})
export class ThemeComponent implements OnInit {
  theme$: Observable<Theme | undefined> | undefined;
  articles$: Observable<Article[]> | undefined;
  isLoading = true;
  sortedArticles: Article[] = [];

  sortOptions = [
    { value: 'recentDate', label: 'Date (récent)', icon: 'calendar_today' },
    { value: 'oldDate', label: 'Date (ancien)', icon: 'calendar_today' },
    { value: 'title', label: 'Titre', icon: 'title' },
    { value: 'author', label: 'Auteur', icon: 'person' },
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private themesService: ThemesService,
    private articleService: ArticleService
  ) {}

  ngOnInit(): void {
    const themeId = this.route.snapshot.paramMap.get('id') || 1;

    this.theme$ = this.themesService.themes$.pipe(
      map((themes) => themes.find((t) => t.id === +themeId))
    );

    this.articles$ = this.theme$.pipe(
      switchMap((theme) => {
        if (!theme?.articleIds || theme?.articleIds?.length == 0) return of([]);
        const articleRequests = theme.articleIds.map((id) =>
          this.articleService.getArticleById(id)
        );
        return forkJoin(articleRequests).pipe(
          timeout({ first: 10000, with: () => of([]) })
        );
      })
    );

    this.articles$.subscribe((articles) => {
      this.sortedArticles = [...articles];
      this.sortArticles('recentDate');
    });
  }

  onSorted(criteria: string): void {
    this.sortArticles(criteria);
  }

  sortArticles(criteria: string): void {
    this.sortedArticles.sort((a, b) => {
      switch (criteria) {
        case 'author':
          return a.authorUsername.localeCompare(b.authorUsername);
        case 'title':
          return a.title.localeCompare(b.title);
        case 'recentDate':
          return (
            new Date(b.updatedAt.replace(' ', 'T')).getTime() -
            new Date(a.updatedAt.replace(' ', 'T')).getTime()
          );
        case 'oldDate':
          return (
            new Date(a.updatedAt.replace(' ', 'T')).getTime() -
            new Date(b.updatedAt.replace(' ', 'T')).getTime()
          );
        default:
          return 0;
      }
    });
  }

  navigateToArticle(articleId: number): void {
    this.router.navigate(['/article', articleId]);
  }

  navigateToCreateArticle(themeId: number): void {
    this.router.navigate(['/create_article']);
  }
}
