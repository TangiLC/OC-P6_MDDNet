package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for creating a new comment")
public class CreateCommentDto {

    @NotBlank
    @Schema(description = "Content of the comment", example = "This article is amazing!")
    private String content;

    @Schema(description = "ID of the article associated with the comment", example = "1")
    private Long articleId;

    /*@Schema(description = "ID of the author (optional)", example = "1")
    private Long authorId;*/
}
