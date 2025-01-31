import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Article } from '../interfaces/articleDto.interface';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private baseUrl = `${environment.apiBaseUrl}api/article`;

  constructor(private httpClient: HttpClient) {}

  getArticleById(id: number): Observable<Article> {
    return this.httpClient.get<Article>(`${this.baseUrl}/${id}`);
  }
  createArticle(article: any): Observable<Article> {
    return this.httpClient.post<Article>(`${this.baseUrl}`, article);
  }

  updateArticle(id: number, article: any): Observable<Article> {
    return this.httpClient.put<Article>(`${this.baseUrl}/${id}`, article);
  }

  deleteArticle(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
  }

  cleanupNewsArticles(): Observable<string> {
    return this.httpClient
      .post<string>(`${this.baseUrl}/cleanup`, {})
      .pipe(map((response) => response.toString()));
  }
}
