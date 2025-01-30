package com.openclassrooms.mddapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = true)
  private User author;

  @ManyToOne
  @JoinColumn(
    name = "article_id",
    referencedColumnName = "id",
    nullable = false
  )
  private Article article;
}
