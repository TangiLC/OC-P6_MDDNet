package com.openclassrooms.mddapi.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "articles")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(nullable = false)
  private LocalDateTime updatedAt = LocalDateTime.now();

  @Column(nullable = false, length = 255)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @ManyToOne
  @JoinColumn(name = "author", referencedColumnName = "id")
  private User author;

  @OneToMany(
    mappedBy = "article",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<Comment> comments = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "article_themes",
    joinColumns = @JoinColumn(name = "article_id"),
    inverseJoinColumns = @JoinColumn(name = "theme_id")
  )
  private Set<Theme> themes = new HashSet<>();

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
