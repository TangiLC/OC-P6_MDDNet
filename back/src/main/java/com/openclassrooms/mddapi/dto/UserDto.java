package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
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
@Schema(description = "User DTO")
public class UserDto {

  @Schema(description = "Unique id for user", example = "1")
  private Long id;

  @NotBlank
  @Size(max = 50)
  @Schema(description = "users email", example = "test@test.com")
  private String email;

  @NotBlank
  @Size(max = 30)
  @Schema(description = "username", example = "myAlias1")
  private String username;

  @NotBlank
  @Size(max = 36)
  @Schema(
    description = "picture uuid",
    example = "90ac3cab-a071-4b0e-b914-212eb78436d2"
  )
  private String picture;

  private Boolean isAdmin;

  @Schema(
    description = "Set of theme IDs associated with the user",
    example = "[1, 2, 3]"
  )
  private List<Long> themesSet;

  @Schema(
    description = "Set of comments IDs associated with the user",
    example = "[11, 22, 33]"
  )
  private List<Long> commentsSet;
}
