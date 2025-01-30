package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Theme;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  Set<Article> findByCreatedAtBefore(LocalDateTime limitDate);

  @Query("SELECT a FROM Article a JOIN a.themes t WHERE t.id = :themeId ORDER BY a.updatedAt DESC")
  Set<Article> findAllByThemeOrderByCreatedAtDesc(@Param("themeId") Long themeId);


  @SuppressWarnings("null")
  @Query("SELECT t FROM Theme t JOIN t.articles a WHERE a.id = :articleId")
  Set<Theme> findThemesByArticleId(@Param("articleId") Long articleId);

  @Query(
    "SELECT c FROM Comment c LEFT JOIN FETCH c.author WHERE c.article.id = :articleId"
  )
  Set<Comment> findCommentsByArticleId(@Param("articleId") Long articleId);

  @Query("SELECT a FROM Article a LEFT JOIN FETCH a.author WHERE a.id = :id")
  Optional<Article> findArticleById(@Param("id") Long id);

  @Query(
    "SELECT DISTINCT a.id FROM Article a JOIN a.themes t WHERE t.id = :themeId"
  )
  Set<Long> findArticleIdsByThemeId(@Param("themeId") Long themeId);

  @Query("SELECT DISTINCT a.id FROM Article a WHERE a.author.id = :authorId")
  Set<Long> findArticleIdsByAuthorId(@Param("authorId") Long authorId);

  @Query(
    value = """
            SELECT COUNT(t.id) 
            FROM Article a 
            JOIN a.themes t 
            WHERE a.id = :articleId
            """
  )
  Long debugCountThemesByArticle(@Param("articleId") Long articleId);
}
