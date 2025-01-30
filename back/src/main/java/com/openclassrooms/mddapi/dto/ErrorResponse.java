package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response object")
public class ErrorResponse {

  @Schema(description = "HTTP status code", example = "400")
  private int statusCode;

  @Schema(description = "Error message", example = "Invalid input data")
  private String message;

  @Schema(
    description = "Timestamp of the error",
    example = "2025-01-14T10:15:30"
  )
  private String timestamp;
}
