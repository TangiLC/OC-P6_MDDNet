package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleCleanupService {

  private final ArticleRepository articleRepository;
  private final ThemeRepository themeRepository;

  /**
   * Nettoyage des articles ancien : on conserve dans le thème NEWS les 6 plus récents
   * Planifié à 2h00 du matin chaque jour
   */
  @Scheduled(cron = "0 0 2 * * *")
  @Transactional
  public void cleanupOldNewsArticles() {
    Theme newsTheme = themeRepository
      .findById(1L)
      .orElseThrow(() ->
        new RuntimeException("Impossible de trouver le thème NEWS (id=1).")
      );

    Set<Article> articlesSet = articleRepository.findAllByThemeOrderByCreatedAtDesc(
      1L
    );
    List<Article> articlesList = new ArrayList<>(articlesSet);

    articlesList.sort(Comparator.comparing(Article::getUpdatedAt).reversed());

    if (articlesList.size() > 6) {
      for (int i = 6; i < articlesList.size(); i++) {
        Article olderArticle = articlesList.get(i);
        olderArticle.getThemes().remove(newsTheme);
        articleRepository.save(olderArticle);
      }
    }
  }
}
