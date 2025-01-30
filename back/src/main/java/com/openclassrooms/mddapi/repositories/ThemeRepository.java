package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Theme;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {


  @Query("SELECT t FROM Theme t")
  List<Theme> findAllThemes();

  @Query("SELECT a.id FROM Article a JOIN a.themes t WHERE t.id = :themeId")
  Set<Long> findArticleIdsByThemeId(@Param("themeId") Long themeId);

}
