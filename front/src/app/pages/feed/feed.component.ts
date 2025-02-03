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
  isFeedEmpty = true;
  sortedArticles: Article[] = [];

  sortOptions = [
    { value: 'recentDate', label: 'Date (récent)', icon: 'calendar_today' },
    { value: 'oldDate', label: 'Date (ancien)', icon: 'calendar_today' },
    { value: 'title', label: 'Titre', icon: 'title' },
    { value: 'author', label: 'Auteur', icon: 'person' },
    { value: 'theme', label: 'Thème', icon: 'list_alt' },
  ];

  constructor(
    private authService: AuthService,
    private articleService: ArticleService,
    private themesService: ThemesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.isFeedEmpty = false;
    this.sortedArticles = [];
    this.articles$ = this.authService.userInfo$.pipe(
      switchMap((userInfo) => {
        if (!userInfo?.themesSet || userInfo.themesSet.length === 0) {
          this.isFeedEmpty = true;
        }
        return this.themesService.themes$.pipe(
          map((themes) => {
            const themeSet = userInfo?.themesSet?.length
              ? userInfo.themesSet
              : [1];
            return themes.filter((theme) => themeSet.includes(theme.id));
          }),
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
        case 'theme':
          return (
            (a.themeIds[0] !== 1 ? a.themeIds[0] : a.themeIds[1]) -
            (b.themeIds[0] !== 1 ? b.themeIds[0] : b.themeIds[1])
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
