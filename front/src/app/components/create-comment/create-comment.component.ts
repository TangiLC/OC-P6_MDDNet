import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { CommentService } from '../../services/comment.service';

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
    MatIconModule,
  ],
})
export class CreateCommentComponent {
  @Input() articleId!: number;
  @Output() commentCreated = new EventEmitter<void>();
  commentForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private commentService: CommentService
  ) {
    this.commentForm = this.fb.group({
      content: ['', [Validators.required, Validators.minLength(3)]],
    });
  }

  onSubmit(): void {
    if (this.commentForm.valid) {
      const commentData = {
        articleId: this.articleId,
        content: this.commentForm.value.content,
      };

      this.commentService.createComment(commentData).subscribe({
        next: (response) => {
          this.snackBar.open('Commentaire créé avec succès !', '', {
            duration: 1500,
          });
          this.commentForm.get('content')?.setValue('');
          this.commentForm.get('content')?.markAsPristine();
          this.commentForm.get('content')?.markAsUntouched();
          this.commentCreated.emit();
        },
        error: (err) => {
          console.error('Erreur lors de la création du commentaire :', err);
          this.snackBar.open('Une erreur est survenue.', '', {
            duration: 2000,
          });
        },
      });
    }
  }
}
