import { MatProgressBarModule } from '@angular/material/progress-bar';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Observable, of } from 'rxjs';
import { ArticleService } from '../../services/article.service';
import { ArticleDetailComponent } from '../../components/article-detail/article-detail.component';
import { CommentDetailComponent } from '../../components/comment-detail/comment-detail.component';
import { Article } from '../../interfaces/article.interface';
import { CreateCommentComponent } from '../../components/create-comment/create-comment.component';
import { AuthService } from '../../services/auth.service';
import { UserInformation } from '../../interfaces/userInformation.interface';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatProgressBarModule,MatButtonModule,
    ArticleDetailComponent,
    CommentDetailComponent,
    CreateCommentComponent,
  ],
})
export class ArticleComponent implements OnInit {
  article$: Observable<Article | null> = of(null);
  article: Article | null = null;
  isAllowed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private articleService: ArticleService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.article$ = this.articleService.getArticleById(+id);

      combineLatest([this.article$, this.authService.userInfo$]).subscribe(
        ([article, userInfo]) => {
          if (article && userInfo) {
            this.article = article;
            this.checkPermission(article, userInfo);

            this.article?.comments.sort((a, b) => {
              const dateA = new Date(a.createdAt.replace(' ', 'T')).getTime();
              const dateB = new Date(b.createdAt.replace(' ', 'T')).getTime();
              return dateB - dateA;
            });
          }
        }
      );
    }
  }

  refreshArticle(): void {
    if (this.article?.id) {
      this.article$ = this.articleService.getArticleById(this.article.id);
      combineLatest([this.article$, this.authService.userInfo$]).subscribe(
        ([article, userInfo]) => {
          if (article && userInfo) {
            this.article = article;
            this.checkPermission(article, userInfo);
          }
        }
      );
    }
  }

  private checkPermission(article: Article, userInfo: UserInformation): void {
    this.isAllowed =
      userInfo.username === article.authorUsername || userInfo.isAdmin === true;
  }

  navigateToUpdateArticle(id: number): void {
    this.router.navigate(['/update_article', id]);
  }
}
