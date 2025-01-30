package com.openclassrooms.mddapi.controllers;

import static java.time.LocalDateTime.now;

import com.openclassrooms.mddapi.dto.ErrorResponse;
import com.openclassrooms.mddapi.dto.UpdateUserDto;
import com.openclassrooms.mddapi.dto.UserAbstractDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.auth.JwtResponse;
import com.openclassrooms.mddapi.dto.auth.LoginRequest;
import com.openclassrooms.mddapi.dto.auth.RegisterRequest;
import com.openclassrooms.mddapi.security.UserPrincipal;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing user.
 */
@Tag(name = "1. User")
@RestController
@RequestMapping("/api")
@Validated
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(
    summary = "Authenticate a user and generate a JWT token.",
    description = "Allows a user to log in using their username or email and password," +
    " and returns a JWT token upon successful authentication.",
    security = {}
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Authentication successful",
        content = @Content(schema = @Schema(implementation = JwtResponse.class))
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Invalid username or password",
        content = @Content
      ),
    }
  )
  @PostMapping("/auth/login")
  public ResponseEntity<?> authenticateUser(
    @RequestBody @Valid LoginRequest loginRequest
  ) {
    try {
      JwtResponse response = userService.authenticateUser(loginRequest);
      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(
          new ErrorResponse(401, "Identifiants incorrects", now().toString())
        );
    }
  }

  @Operation(
    summary = "Register a new user.",
    description = "Allows a new user to create an account by providing" +
    " their username, email, and password."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "User registered successfully",
        content = @Content
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid input or user already exists",
        content = @Content
      ),
    }
  )
  @PostMapping("/auth/register")
  public ResponseEntity<?> registerUser(
    @RequestBody @Valid RegisterRequest registerRequest
  ) {
    try {
      userService.registerUser(registerRequest);
      Map<String, String> response = new HashMap<>();
      response.put("message", "User registered successfully!");
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("message", e.getMessage());
      return ResponseEntity.badRequest().body(errorResponse);
    }
  }

  @Operation(
    summary = "Retrieve the authenticated user's information.",
    description = "Fetches the details of the currently authenticated user."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "User information retrieved successfully",
        content = @Content(schema = @Schema(implementation = UserDto.class))
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Unauthorized access",
        content = @Content
      ),
    }
  )
  @Transactional
  @GetMapping("/me")
  public ResponseEntity<UserDto> getAuthenticatedUser(
    @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    if (userPrincipal == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    UserDto userDto = userService.getUserDetails(userPrincipal.getUsername());
    return ResponseEntity.ok(userDto);
  }

  /**
   * Retrieve specific user's information by ID.
   *
   * @param id the ID of the user to retrieve information for
   * @return ResponseEntity containing user's username and picture
   */
  @Operation(
    summary = "Retrieve summary of user information by username",
    description = "Fetches summary a user by their username."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "User information retrieved successfully",
        content = @Content(schema = @Schema(implementation = UserAbstractDto.class))
      ),
      @ApiResponse(
        responseCode = "404",
        description = "User not found",
        content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @GetMapping("/user/{username}")
  public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
    try {
      UserAbstractDto userDetails = userService.getUserDetailsFromUsername(username);
      return ResponseEntity.ok(userDetails);
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(404, e.getMessage(), now().toString()));
    }
  }

  @Operation(
    summary = "Update user information",
    description = "Updates the username, password, and/or picture of a user"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "User updated successfully",
        content = @Content(schema = @Schema(implementation = UserDto.class))
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid input data",
        content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Access denied - Can only update own profile",
        content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "User not found",
        content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @PutMapping("/user/{id}")
  public ResponseEntity<?> updateUser(
    @PathVariable Long id,
    @RequestBody @Valid UpdateUserDto updateUserDto,
    @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    try {
      JwtResponse updatedUser = userService.updateUser(
        id,
        updateUserDto,
        userPrincipal
      );
      return ResponseEntity.ok(updatedUser);
    } catch (AccessDeniedException e) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(new ErrorResponse(403, e.getMessage(), now().toString()));
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(404, e.getMessage(), now().toString()));
    }
  }

  @Operation(
    summary = "Add theme to user's themes",
    description = "Adds a theme to the user's set of themes"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Theme added successfully",
        content = @Content(schema = @Schema(implementation = UserDto.class))
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Access denied",
        content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "User or theme not found",
        content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @PutMapping("/user/add_theme/{id}")
  public ResponseEntity<?> addThemeToUser(
    @PathVariable Long id,
    @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    try {
      UserDto updatedUser = userService.addThemeToUser(id, userPrincipal);
      return ResponseEntity.ok(updatedUser);
    } catch (AccessDeniedException e) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(new ErrorResponse(403, e.getMessage(), now().toString()));
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(404, e.getMessage(), now().toString()));
    }
  }

  @Operation(
    summary = "Remove theme from user's themes",
    description = "Removes a theme from the user's set of themes"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Theme removed successfully",
        content = @Content(schema = @Schema(implementation = UserDto.class))
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Access denied",
        content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "User or theme not found",
        content = @Content(
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @PutMapping("/user/remove_theme/{id}")
  public ResponseEntity<?> removeThemeFromUser(
    @PathVariable Long id,
    @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    try {
      UserDto updatedUser = userService.removeThemeFromUser(id, userPrincipal);
      return ResponseEntity.ok(updatedUser);
    } catch (AccessDeniedException e) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(new ErrorResponse(403, e.getMessage(), now().toString()));
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(404, e.getMessage(), now().toString()));
    }
  }
}
