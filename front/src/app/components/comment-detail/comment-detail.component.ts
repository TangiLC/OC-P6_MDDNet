import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { CommentService } from '../../services/comment.service';
import { filter, Observable, of, switchMap } from 'rxjs';

@Component({
  selector: 'app-comment-detail',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule],
  templateUrl: './comment-detail.component.html',
  styleUrls: ['./comment-detail.component.scss'],
})
export class CommentDetailComponent {
  @Input() comment: {
    id: number;
    content: string;
    createdAt: string;
    authorUsername: string;
    authorPicture:string;
    articleId: number;
  } | null = null;

  constructor(private commentService: CommentService) {}

  ngOnInit(): void {}
}
