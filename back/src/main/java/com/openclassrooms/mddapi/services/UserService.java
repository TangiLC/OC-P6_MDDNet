// UserService.java
package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.UpdateUserDto;
import com.openclassrooms.mddapi.dto.UserAbstractDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.auth.JwtResponse;
import com.openclassrooms.mddapi.dto.auth.LoginRequest;
import com.openclassrooms.mddapi.dto.auth.RegisterRequest;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.UserPrincipal;
import com.openclassrooms.mddapi.security.utils.JwtTokenUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserRepository userRepository;
  private final ThemeRepository themeRepository;
  private final PasswordEncoder passwordEncoder;
  private final ServicesUtils utils;

  public JwtResponse authenticateUser(LoginRequest loginRequest) {
    String login = loginRequest.getUserIdentity();
    User user = userRepository
      .findByUsernameOrEmail(login, login)
      .orElseThrow(() ->
        new BadCredentialsException("password not matching user")
      );

    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        user.getUsername(),
        loginRequest.getPassword()
      )
    );

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String jwt = jwtTokenUtil.generateToken((UserPrincipal) userDetails);
    return new JwtResponse(jwt);
  }

  public void registerUser(RegisterRequest registerRequest) {
    if (
      userRepository.existsByUsernameOrEmail(
        registerRequest.getUsername(),
        registerRequest.getEmail()
      )
    ) {
      throw new IllegalArgumentException("Username or email is already taken!");
    }

    User user = new User();
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setIsAdmin(false);

    userRepository.save(user);
  }

  public UserDto getUserDetails(String username) {
    User user = utils.validateUserName(username);

    List<Long> themesIds = user.getThemes().stream().map(Theme::getId).toList();
    List<Long> commentsIds;
    commentsIds = user.getComments().stream().map(Comment::getId).toList();

    return new UserDto(
      user.getId(),
      user.getEmail(),
      user.getUsername(),
      user.getPicture(),
      user.getIsAdmin(),
      themesIds,
      commentsIds
    );
  }

  /**
   * Fetch user details (username and picture) by user ID.
   *
   * @param id the ID of the user to retrieve
   * @return a Map containing username and picture of the user
   * @throws IllegalArgumentException if the user does not exist
   */
  public UserAbstractDto getUserDetailsFromUsername(String username) {
    User user = userRepository
      .findByUsername(username)
      .orElseThrow(() ->
        new IllegalArgumentException("User not found with username: " + username)
      );
    return UserAbstractDto
      .builder()
      .id(user.getId())
      .email(user.getEmail())
      .username(user.getUsername())
      .picture(user.getPicture())
      .build();
  }

  @Transactional
  public JwtResponse updateUser(
    Long id,
    UpdateUserDto updateUserDto,
    UserPrincipal userPrincipal
  ) {
    User user = utils.validateUserId(id);

    utils.validateAdminOrAuthenticatedUser(user);

    // Mise à jour du username uniquement si présent dans le DTO
    Optional
      .ofNullable(updateUserDto.getUsername())
      .filter(username -> !username.isBlank())
      .ifPresent(newUsername -> {
        if (
          !user.getUsername().equals(newUsername) &&
          userRepository.existsByUsernameOrEmail(newUsername, "")
        ) {
          throw new IllegalArgumentException("Username is already taken");
        }
        user.setUsername(newUsername);
      });

     // Mise à jour de l'email uniquement si présent dans le DTO
     Optional
     .ofNullable(updateUserDto.getEmail())
     .filter(email -> !email.isBlank())
     .ifPresent(newEmail -> {
         if (
             !user.getEmail().equals(newEmail) &&
             userRepository.existsByUsernameOrEmail("", newEmail)
         ) {
             throw new IllegalArgumentException("Email is already taken");
         }
         user.setEmail(newEmail);
     });

    // Mise à jour de l'image uniquement si présente dans le DTO
    Optional.ofNullable(updateUserDto.getPicture()).ifPresent(user::setPicture);

    User updatedUser = userRepository.save(user);

    SecurityContextHolder.clearContext();

    // Créer un nouveau UserPrincipal basé sur l'utilisateur mis à jour
    UserPrincipal newUserPrincipal = new UserPrincipal(updatedUser);

    // Générer un nouveau token
    String newToken = jwtTokenUtil.generateToken(newUserPrincipal);

    // Retourner la même structure que le login
    return new JwtResponse(newToken);
  }

  @Transactional
  public UserDto addThemeToUser(Long themeId, UserPrincipal userPrincipal) {
    User user = utils.validateUserName(userPrincipal.getUsername());

    Theme theme = themeRepository
      .findById(themeId)
      .orElseThrow(() ->
        new RuntimeException("Theme not found with id: " + themeId)
      );

    user.getThemes().add(theme);
    userRepository.save(user);

    return getUserDetails(user.getUsername());
  }

  @Transactional
  public UserDto removeThemeFromUser(
    Long themeId,
    UserPrincipal userPrincipal
  ) {
    User user = utils.validateUserName(userPrincipal.getUsername());

    Theme theme = themeRepository
      .findById(themeId)
      .orElseThrow(() ->
        new RuntimeException("Theme not found with id: " + themeId)
      );

    user.getThemes().remove(theme);
    userRepository.save(user);

    return getUserDetails(user.getUsername());
  }
}
