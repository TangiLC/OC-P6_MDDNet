package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Article DTO")
public class ArticleDto {

  @Schema(description = "Unique id for article", example = "1")
  private Long id;

  @NotBlank
  @Size(max = 255)
  @Schema(
    description = "Title of the article",
    example = "Introduction to Spring Boot"
  )
  private String title;

  @NotBlank
  @Schema(
    description = "Content of the article",
    example = "This is a detailed guide about Spring Boot..."
  )
  private String content;

  @Schema(description = "Creation timestamp of the article")
  private LocalDateTime createdAt;

  @Schema(description = "Update timestamp of the article")
  private LocalDateTime updatedAt;

  @Schema(description = "Username of the author", example = "johndoe")
  private String authorUsername;

  @Schema(description = "Picture ref of the author", example = "profil1")
  private String authorPicture;

  @Schema(description = "Set of theme IDs associated with the article")
  private Set<Long> themeIds;

  @Schema(description = "Set of comments IDs associated with the article")
  private Set<CommentDto> comments;
}
