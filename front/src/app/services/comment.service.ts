import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { CreateComment } from '../interfaces/createCommentDto.interface';
import { CommentDto } from '../interfaces/commentDto.interface';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private userApiUrl = `${environment.apiBaseUrl}api/`;

  constructor(private httpClient: HttpClient) {}

  createComment(createCommentDto: CreateComment): Observable<CommentDto> {
    return this.httpClient.post<CommentDto>(
      this.userApiUrl + 'comment',
      createCommentDto
    );
  }
}
