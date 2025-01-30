package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {

  @Schema(description = "Unique id for theme", example = "1")
  private Long id;

  @NotBlank(message = "Title is required")
  @Size(max = 255, message = "Title must be less than 255 characters")
  @Schema(description = "Title of the Theme", example = "Spring-boot")
  private String title;

  @NotBlank(message = "Description is required")
  @Schema(
    description = "Description of the Theme",
    example = "Everything about Spring-boot"
  )
  private String description;

  @Size(max = 36)
  @Schema(
    description = "uuid name of Theme icon",
    example = "550e8400-e29b-41d4-a716-446655440000"
  )
  private String icon;

  @Pattern(
    regexp = "^[0-9A-Fa-f]{6}$",
    message = "Color must be a valid 6-character hex code"
  )
  @Schema(description = "hexa code of Theme color", example = "aa3f80")
  private String color;

  @Schema(description = "Set of related article")
  private Set<Long> articleIds;
}
