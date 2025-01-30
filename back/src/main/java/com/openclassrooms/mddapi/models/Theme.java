package com.openclassrooms.mddapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "themes")
public class Theme {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  //@Column(nullable = false, unique = true, name = "id")
  private Long id;

  @Column(nullable = false, length = 255)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Column(length = 36)
  private String icon;

  @Column(length = 6)
  private String color;

  @ManyToMany(mappedBy = "themes")
  private Set<Article> articles = new HashSet<>();
}
