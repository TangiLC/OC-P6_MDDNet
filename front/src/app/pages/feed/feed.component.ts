import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { Observable, forkJoin, of, switchMap, map, finalize } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { ArticleService } from '../../services/article.service';
import { ThemesService } from '../../services/theme.service';
import { Article } from '../../interfaces/articleDto.interface';
import { ArticleDetailComponent } from '../../components/article-detail/article-detail.component';
import { SortFormComponent } from '../../components/sort-form/sort-form.component';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss'],
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
export class FeedComponent implements OnInit {
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
    private authService: AuthService,
    private articleService: ArticleService,
    private themesService: ThemesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.articles$ = this.authService.userInfo$.pipe(
      switchMap((userInfo) => {
        if (!userInfo?.themesSet || userInfo.themesSet.length === 0) {
          return of([]);
        }
        return this.themesService.themes$.pipe(
          map((themes) =>
            themes.filter((theme) => userInfo.themesSet.includes(theme.id))
          ),
          switchMap((userThemes) => {
            const articleRequests = userThemes.flatMap((theme) =>
              theme.articleIds.map((id) =>
                this.articleService.getArticleById(id)
              )
            );
            return articleRequests.length > 0
              ? forkJoin(articleRequests)
              : of([]);
          })
        );
      }),
      finalize(() => {
        this.isLoading = false;
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
}
