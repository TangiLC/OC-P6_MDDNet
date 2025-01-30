package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User Update DTO")
public class UpdateUserDto {
    @Size(max = 30)
    @Schema(description = "username", example = "myAlias1|null")
    private String username;

    @Email
    @Size(max = 50)
    @Schema(description = "email", example = "user@example.com|null")
    private String email;

    @Size(max = 36)
    @Schema(description = "picture uuid", example = "90ac3cab|null")
    private String picture;
}
