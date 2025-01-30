package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserAbstractDto {

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

}
