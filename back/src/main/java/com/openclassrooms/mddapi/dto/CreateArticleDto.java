package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "article creation DTO")
public class CreateArticleDto {

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

  @NotNull
  @Schema(
    description = "Set of theme IDs to associate with the article",
    example = "[1, 2, 3]"
  )
  private List<Long> themeIds;
}
