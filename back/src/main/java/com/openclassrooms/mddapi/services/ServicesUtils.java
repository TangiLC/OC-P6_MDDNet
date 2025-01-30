package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ServicesUtils {

  private final ArticleRepository articleRepository;
  private final ThemeRepository themeRepository;
  private final UserRepository userRepository;

  public User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder
      .getContext()
      .getAuthentication();
    if (
      authentication != null &&
      authentication.getPrincipal() instanceof UserPrincipal
    ) {
      UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
      return userPrincipal.getUser();
    }
    throw new RuntimeException("No authenticated user found");
  }

  public void validateAdminOrAuthenticatedUser(User author) {
    User authenticatedUser = getAuthenticatedUser();
    boolean isAuthor = author.getId().equals(authenticatedUser.getId());
    boolean isAdmin =
      authenticatedUser.getIsAdmin() != null && authenticatedUser.getIsAdmin();

    if (!isAuthor && !isAdmin) {
      throw new AccessDeniedException(
        "You can only modify your own data or must be an admin"
      );
    }
  }

  public User validateUser(UserPrincipal userPrincipal) {
    return userRepository
      .findByUsername(userPrincipal.getUsername())
      .orElseThrow(() -> new RuntimeException("User not found"));
  }

  public User validateUserId(Long id) {
    return userRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
  }

  public User validateUserName(String username) {
    return userRepository
      .findByUsername(username)
      .orElseThrow(() ->
        new RuntimeException("User not found with username: " + username)
      );
  }

  public Article validateArticleId(Long articleId) {
    return articleRepository
      .findArticleById(articleId)
      .orElseThrow(() ->
        new RuntimeException("Article not found with ID: " + articleId)
      );
  }


  public Theme validateThemeId(Long themeId) {
    return themeRepository
      .findById(themeId)
      .orElseThrow(() ->
        new RuntimeException("Theme not found with ID: " + themeId)
      );
  }

  public void validateAdminUser() {
    User authenticatedUser = getAuthenticatedUser();

    boolean isAdmin =
      authenticatedUser.getIsAdmin() != null && authenticatedUser.getIsAdmin();

    if (!isAdmin) {
      throw new AccessDeniedException("Operation available to admin only");
    }

  }
}
