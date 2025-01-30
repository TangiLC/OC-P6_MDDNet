package com.openclassrooms.mddapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.exceptions.CustomBadCredentialsException;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.UserPrincipal;
import com.openclassrooms.mddapi.security.utils.JwtTokenUtil;

@Service
public class AuthenticationService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UserRepository userRepository;

  public String authenticate(String username, String password)
    throws Exception {
    try {
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
      );

      if (authentication.isAuthenticated()) {
        User user = userRepository
          .findByUsername(username)
          .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        UserPrincipal userPrincipal = new UserPrincipal(user);
        return jwtTokenUtil.generateToken(userPrincipal);
      } else {
        throw new CustomBadCredentialsException("Authentification échouée");
      }
    } catch (AuthenticationException e) {
      throw new CustomBadCredentialsException(
        "Nom d'utilisateur ou mot de passe incorrect"
      );
    }
  }
}
