package com.openclassrooms.mddapi.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = { "comments", "themes" })
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true, name = "id")
  private Long id;

  @Column(nullable = false, unique = true, length = 50, name = "email")
  private String email;

  @Column(nullable = false, unique = true, length = 30, name = "username")
  private String username;

  @Column(nullable = false, length = 255, name = "password")
  private String password;

  @Column(length = 36, name = "picture")
  private String picture;

  @Column(nullable = false, name = "is_admin")
  private Boolean isAdmin;

  @OneToMany(
    mappedBy = "author",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<Comment> comments = new HashSet<>();

  @ManyToMany
  @JoinTable(
    name = "user_themes",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "theme_id")
  )
  private Set<Theme> themes = new HashSet<>();
}
