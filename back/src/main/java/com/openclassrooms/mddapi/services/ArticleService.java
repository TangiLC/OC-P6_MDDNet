package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.CreateArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final ThemeRepository themeRepository;
  private final UserRepository userRepository;
  private final ServicesUtils utils;

  @Transactional
  public ArticleDto createOrUpdateArticle(
    Long id,
    CreateArticleDto createArticleDto
  ) {
    List<Theme> themes = themeRepository.findAllById(
      createArticleDto.getThemeIds()
    );
    Theme newsTheme = themeRepository //ajout auto à thème 1=NEWS qui sera cleanup dans 21j
      .findById(1L)
      .orElseThrow(() ->
        new RuntimeException("Impossible de trouver le thème NEWS (id=1)")
      );
    if (!themes.contains(newsTheme)) {
      themes.add(newsTheme);
    }

    if (themes.isEmpty()) {
      throw new RuntimeException("theme is missing");
    }

    Article article;
    if (id == null) {
      // Create logic
      article = new Article();
      article.setAuthor(utils.getAuthenticatedUser());
    } else {
      // Update logic
      article = utils.validateArticleId(id);
      utils.validateAdminOrAuthenticatedUser(article.getAuthor());
    }

    article.setTitle(createArticleDto.getTitle());
    article.setContent(createArticleDto.getContent());

    Article savedArticle = articleRepository.save(article);

    Set<Theme> themeSet = new HashSet<>(themes);
    savedArticle.setThemes(themeSet);
    savedArticle = articleRepository.save(article);

    return toDto(savedArticle);
  }

  public void deleteArticle(Long id) {
    Article article = utils.validateArticleId(id);

    utils.validateAdminOrAuthenticatedUser(article.getAuthor());

    articleRepository.delete(article);
  }

  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public ArticleDto getArticleById(Long articleId) {
    Article article = utils.validateArticleId(articleId);

    Set<Theme> themes = articleRepository.findThemesByArticleId(articleId);
    article.setThemes(themes);
    Set<Comment> comments = articleRepository.findCommentsByArticleId(
      articleId
    );
    article.setComments(comments);

    return toDto(article);
  }

  @Transactional(readOnly = true)
  public Set<ArticleDto> getArticlesByAuthorId(Long authorId) {
    if (!userRepository.existsById(authorId)) {
      throw new EntityNotFoundException(
        "Author not found with ID: " + authorId
      );
    }

    Set<Long> articleIds = articleRepository.findArticleIdsByAuthorId(authorId);
    Set<ArticleDto> articles = new HashSet<>();

    addArticlesToHashSet(articleIds, articles);

    return articles;
  }

  @Transactional(readOnly = true)
  public Set<ArticleDto> getArticlesByThemeId(Long themeId) {
    if (!themeRepository.existsById(themeId)) {
      throw new RuntimeException("Theme not found with ID: " + themeId);
    }

    Set<Long> articleIds = articleRepository.findArticleIdsByThemeId(themeId);
    Set<ArticleDto> articles = new HashSet<>();

    addArticlesToHashSet(articleIds, articles);

    return articles;
  }

  private void addArticlesToHashSet(
    Set<Long> articleIds,
    Set<ArticleDto> articles
  ) {
    for (Long articleId : articleIds) {
      try {
        ArticleDto articleDto = this.getArticleById(articleId);
        articles.add(articleDto);
      } catch (RuntimeException e) {
        log.warn("Could not load article with ID: {}", articleId, e);
      }
    }
  }

  @Transactional(readOnly = true)
  public ArticleDto toDto(Article article) {
    Set<Long> themeIds = article.getThemes() != null
      ? article
        .getThemes()
        .stream()
        .map(Theme::getId)
        .collect(Collectors.toSet())
      : new HashSet<>();

    Set<CommentDto> comments = article.getComments() != null
      ? article
        .getComments()
        .stream()
        .map(comment ->
          CommentDto
            .builder()
            .id(comment.getId())
            .content(comment.getContent())
            .createdAt(comment.getCreatedAt())
            .authorUsername(comment.getAuthor().getUsername())
            .authorPicture(comment.getAuthor().getPicture())
            .articleId(article.getId())
            .build()
        )
        .collect(Collectors.toSet())
      : new HashSet<>();

    return ArticleDto
      .builder()
      .id(article.getId())
      .title(article.getTitle())
      .content(article.getContent())
      .createdAt(article.getCreatedAt())
      .updatedAt(article.getUpdatedAt())
      .authorUsername(
        article.getAuthor() != null ? article.getAuthor().getUsername() : null
      )
      .authorPicture(
        article.getAuthor() != null ? article.getAuthor().getPicture() : null
      )
      .themeIds(themeIds)
      .comments(comments)
      .build();
  }
}
