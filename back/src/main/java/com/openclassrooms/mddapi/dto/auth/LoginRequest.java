package com.openclassrooms.mddapi.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

  @NotBlank
  private String userIdentity;

  @NotBlank
  private String password;

  public String getUserIdentity() {
    return userIdentity;
  }

  public void setUserIdentity(String userIdentity) {
    this.userIdentity = userIdentity;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
