package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.CreateCommentDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.security.UserPrincipal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final ArticleRepository articleRepository;

  public CommentDto createComment(CreateCommentDto createCommentDto) {
    Comment comment = new Comment();
    if (createCommentDto.getArticleId() != null) {
      Article article = articleRepository
        .findById(createCommentDto.getArticleId())
        .orElseThrow(() ->
          new RuntimeException(
            "Article not found with ID: " + createCommentDto.getArticleId()
          )
        );
      comment.setArticle(article);
    }

    comment.setContent(createCommentDto.getContent());

    comment.setAuthor(getAuthenticatedUser());
    Comment savedComment = commentRepository.save(comment);
    return toDto(savedComment);
  }

  public List<CommentDto> getCommentsByArticle(Long articleId) {
    return commentRepository
      .findByArticleId(articleId)
      .stream()
      .map(this::toDto)
      .collect(Collectors.toList());
  }

  public List<CommentDto> getCommentsByAuthor(Long authorId) {
    return commentRepository
      .findByAuthorId(authorId)
      .stream()
      .map(this::toDto)
      .collect(Collectors.toList());
  }

  public CommentDto getCommentById(Long id) {
    Comment comment = commentRepository
      .findById(id)
      .orElseThrow(() ->
        new RuntimeException("Comment not found with ID: " + id)
      );
    return toDto(comment);
  }

  public CommentDto updateComment(Long id, CommentDto updatedCommentDto) {
    Comment comment = commentRepository
      .findById(id)
      .orElseThrow(() ->
        new RuntimeException("Comment not found with ID: " + id)
      );
    User authenticatedUser = getAuthenticatedUser();
    if (!comment.getAuthor().getId().equals(authenticatedUser.getId())) {
      throw new AccessDeniedException("You can only modify your own comments");
    }

    comment.setContent(updatedCommentDto.getContent());
    Comment updatedComment = commentRepository.save(comment);
    return toDto(updatedComment);
  }

  public void deleteComment(Long id) {
    Comment comment = commentRepository
      .findById(id)
      .orElseThrow(() ->
        new RuntimeException("Comment not found with ID: " + id)
      );

    User authenticatedUser = getAuthenticatedUser();

    boolean isAuthor = comment
      .getAuthor()
      .getId()
      .equals(authenticatedUser.getId());
    boolean isAdmin =
      authenticatedUser.getIsAdmin() != null && authenticatedUser.getIsAdmin();

    if (!isAuthor && !isAdmin) {
      throw new AccessDeniedException(
        "You can only delete your own comments or must be an admin"
      );
    }
    commentRepository.delete(comment);
  }

  private CommentDto toDto(Comment comment) {
    String currentUsername = getAuthenticatedUser().getUsername();
    return CommentDto
      .builder()
      .id(comment.getId())
      .content(comment.getContent())
      .createdAt(comment.getCreatedAt())
      .authorUsername(
        comment.getAuthor() != null
          ? comment.getAuthor().getUsername()
          : currentUsername
      )
      .articleId(
        comment.getArticle() != null ? comment.getArticle().getId() : null
      )
      .build();
  }

  private User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder
      .getContext()
      .getAuthentication();
    if (
      authentication != null &&
      authentication.getPrincipal() instanceof UserPrincipal
    ) {
      UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
      return userPrincipal.getUser();
    }
    throw new RuntimeException("No authenticated user found");
  }
}
