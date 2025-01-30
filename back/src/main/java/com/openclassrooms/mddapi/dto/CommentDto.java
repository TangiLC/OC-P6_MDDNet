package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Comment DTO")
public class CommentDto {

    @Schema(description = "Unique id for the comment", example = "1")
    private Long id;

    @NotBlank
    @Schema(description = "Content of the comment", example = "This is a great article!")
    private String content;

    @Schema(description = "Creation timestamp of the comment", example = "2025-01-01T10:10:00")
    private LocalDateTime createdAt;

    @Schema(description = "Username of the author", example = "Bob1")
    private String authorUsername;

    @Schema(description = "Picture name of the author", example = "Profil1")
    private String authorPicture;


    @Schema(description = "Article ID associated with the comment", example = "1")
    private Long articleId;
}
