package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.CreateCommentDto;
import com.openclassrooms.mddapi.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing comments.
 */
@Tag(name = "4. Comments")
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @Operation(summary = "Create a new comment")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "201",
        description = "Comment created successfully"
      ),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
    }
  )
  @PostMapping("comment")
  public ResponseEntity<CommentDto> createComment(
    @RequestBody CreateCommentDto createCommentDto
  ) {
    CommentDto createdComment = commentService.createComment(createCommentDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
  }

  @Operation(summary = "Get a comment by ID")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "Comment retrieved successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = CommentDto.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Comment not found",
        content = @Content(mediaType = "application/json")
      ),
    }
  )
  @GetMapping("comment/{id}")
  public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
    CommentDto comment = commentService.getCommentById(id);
    return ResponseEntity.ok(comment);
  }

  @Operation(summary = "Update a comment by ID")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "Comment updated successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = CommentDto.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid input data",
        content = @Content(mediaType = "application/json")
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Comment not found",
        content = @Content(mediaType = "application/json")
      ),
    }
  )
  @PutMapping("comment/{id}")
  public ResponseEntity<String> updateComment(
    @PathVariable Long id,
    @RequestBody @Valid CommentDto updatedCommentDto
  ) {
    CommentDto updatedComment = commentService.updateComment(
      id,
      updatedCommentDto
    );
    return ResponseEntity.ok("content:" + updatedComment.getContent());
  }

  @Operation(summary = "Delete a comment by ID")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "204",
        description = "Comment deleted successfully"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Comment not found",
        content = @Content(mediaType = "application/json")
      ),
    }
  )
  @DeleteMapping("comment/{id}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
    commentService.deleteComment(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Get comments by article")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "Comments retrieved successfully"
      ),
      @ApiResponse(responseCode = "404", description = "Article not found"),
    }
  )
  @GetMapping("comments/by_article/{articleId}")
  public ResponseEntity<List<CommentDto>> getCommentsByArticle(
    @PathVariable Long articleId
  ) {
    List<CommentDto> comments = commentService.getCommentsByArticle(articleId);
    return ResponseEntity.ok(comments);
  }

  @Operation(summary = "Get comments by author")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "Comments retrieved successfully"
      ),
      @ApiResponse(responseCode = "404", description = "Author not found"),
    }
  )
  @GetMapping("comments/by_author/{authorId}")
  public ResponseEntity<List<CommentDto>> getCommentsByAuthor(
    @PathVariable Long authorId
  ) {
    List<CommentDto> comments = commentService.getCommentsByAuthor(authorId);
    return ResponseEntity.ok(comments);
  }
}
