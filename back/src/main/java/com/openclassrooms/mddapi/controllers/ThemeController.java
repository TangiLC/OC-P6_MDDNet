package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CreateThemeDto;
import com.openclassrooms.mddapi.dto.ErrorResponse;
import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.services.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing themes.
 */
@Tag(name = "3. Theme")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ThemeController {

  private final ThemeService themeService;

  @Operation(summary = "Create a new theme")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "201",
        description = "Theme created successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ThemeDto.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid input data",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Access denied - Only admin can create themes",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @PostMapping("/theme")
  public ResponseEntity<ThemeDto> createTheme(
    @RequestBody @Valid CreateThemeDto createThemeDto
  ) {
    ThemeDto createdTheme = themeService.createOrUpdateTheme(
      null,
      createThemeDto
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTheme);
  }

  @Operation(summary = "Get theme by id")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "Theme retrieved successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ThemeDto.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Theme not found",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @GetMapping("/theme/{id}")
  public ResponseEntity<ThemeDto> getThemeById(@PathVariable Long id) {
    ThemeDto theme = themeService.getThemeById(id);
    return ResponseEntity.ok(theme);
  }

  @Operation(summary = "Get all themes")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "Themes retrieved successfully",
        content = @Content(
          mediaType = "application/json",
          array = @ArraySchema(
            schema = @Schema(implementation = ThemeDto.class)
          )
        )
      ),
    }
  )
  @GetMapping("/themes")
  public ResponseEntity<Set<ThemeDto>> getAllThemes() {
    Set<ThemeDto> themes = themeService.getAllThemes();
    return ResponseEntity.ok(themes);
  }

  @Operation(summary = "Update a theme")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "Theme updated successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ThemeDto.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid input data",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Access denied - Only admin can update themes",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Theme not found",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @PutMapping("/theme/{id}")
  public ResponseEntity<ThemeDto> updateTheme(
    @PathVariable Long id,
    @RequestBody @Valid CreateThemeDto updateThemeDto
  ) {
    ThemeDto updatedTheme = themeService.createOrUpdateTheme(
      id,
      updateThemeDto
    );
    return ResponseEntity.ok(updatedTheme);
  }

  @Operation(summary = "Delete a theme")
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "204",
        description = "Theme deleted successfully"
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Access denied - Only admin can delete themes",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Theme not found",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @DeleteMapping("/theme/{id}")
  public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
    themeService.deleteTheme(id);
    return ResponseEntity.noContent().build();
  }
}
