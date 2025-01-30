package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.CreateArticleDto;
import com.openclassrooms.mddapi.dto.ErrorResponse;
import com.openclassrooms.mddapi.services.ArticleCleanupService;
import com.openclassrooms.mddapi.services.ArticleService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing articles.
 */
@Tag(name = "2. Article")
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleService articleService;
  private final ArticleCleanupService articleCleanupService;

  @Operation(summary = "Create a new article")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "201",
        description = "Article created successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ArticleDto.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid input data",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Access denied",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @PostMapping
  public ResponseEntity<ArticleDto> createArticle(
    @RequestBody @Valid CreateArticleDto createArticleDto
  ) {
    ArticleDto createdArticle = articleService.createOrUpdateArticle(
      null,
      createArticleDto
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
  }

  /**
   * Get article by id.
   *
   * @param articleId the ID of the article
   * @return ArticleDto object
   */
  @Operation(
    summary = "Get article by id",
    description = "Retrieves article by its id."
  )
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "Article retrieved successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ArticleDto.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Author not found",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @Transactional(readOnly = true)
  @GetMapping("/{id}")
  public ResponseEntity<ArticleDto> getById(@PathVariable Long id) {
    ArticleDto article = articleService.getArticleById(id);
    return ResponseEntity.ok(article);
  }

  /**
   * Update an article by its ID.
   *
   * @param id              the ID of the article to update
   * @param createArticleDto the data transfer object containing updated article details
   * @return the updated article as an ArticleDto
   */
  @Operation(
    summary = "Update an article",
    description = "Updates an existing article with the provided details."
  )
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "Article updated successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ArticleDto.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid input data",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Access denied - Only the author can update their article",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Article not found",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @Transactional
  @PutMapping("/{id}")
  public ResponseEntity<ArticleDto> updateArticle(
    @PathVariable Long id,
    @RequestBody @Valid CreateArticleDto updateArticleDto
  ) {
    ArticleDto updatedArticle = articleService.createOrUpdateArticle(
      id,
      updateArticleDto
    );
    return ResponseEntity.ok(updatedArticle);
  }

  /**
   * Delete an article by its ID.
   *
   * @param id the ID of the article to delete
   * @return a ResponseEntity with no content
   */
  @Operation(
    summary = "Delete an article",
    description = "Deletes an article by its ID."
  )
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "204",
        description = "Article deleted successfully"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Access denied - Only the author or admin can delete the article",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Article not found",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
    articleService.deleteArticle(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Get all articles associated with a specific theme.
   *
   * @param themeId the ID of the theme
   * @return a set of articles as ArticleDto objects
   */
  @Operation(
    summary = "Get articles by theme",
    description = "Retrieves all articles associated with the specified theme."
  )
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "Articles retrieved successfully",
        content = @Content(
          mediaType = "application/json",
          array = @ArraySchema(
            schema = @Schema(implementation = ArticleDto.class)
          )
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Theme not found",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @Transactional(readOnly = true)
  @GetMapping("/by_theme/{themeId}")
  public ResponseEntity<Set<ArticleDto>> getByTheme(
    @PathVariable Long themeId
  ) {
    Set<ArticleDto> articles = articleService.getArticlesByThemeId(themeId);
    return ResponseEntity.ok(articles);
  }

  /**
   * Get all articles created by a specific author.
   *
   * @param authorId the ID of the author
   * @return a set of articles as ArticleDto objects
   */
  @Operation(
    summary = "Get articles by author",
    description = "Retrieves all articles created by the specified author."
  )
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "Articles retrieved successfully",
        content = @Content(
          mediaType = "application/json",
          array = @ArraySchema(
            schema = @Schema(implementation = ArticleDto.class)
          )
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Author not found",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @Transactional(readOnly = true)
  @GetMapping("/by_author/{authorId}")
  public ResponseEntity<Set<ArticleDto>> getByAuthor(
    @PathVariable Long authorId
  ) {
    Set<ArticleDto> articles = articleService.getArticlesByAuthorId(authorId);
    return ResponseEntity.ok(articles);
  }

  /**
   * Force cleanup of anciens articles in theme NEWS
   *
   * @return ResponseEntity with message confirmation
   */
  @PostMapping("/cleanup")
  @Hidden
  public ResponseEntity<Map<String, String>> forceCleanup() {
    try {
      articleCleanupService.cleanupOldNewsArticles();
      return ResponseEntity.ok(
        Map.of("message", "Nettoyage des articles effectué avec succès")
      );
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
          Map.of(
            "message",
            "Une erreur est survenue lors du nettoyage des articles: " +
            e.getMessage()
          )
        );
    }
  }
}
