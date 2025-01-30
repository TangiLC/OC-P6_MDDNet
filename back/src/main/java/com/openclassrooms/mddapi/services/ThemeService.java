package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.CreateThemeDto;
import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ThemeService {

  private final ThemeRepository themeRepository;
  private final ServicesUtils utils;

  @Transactional
  public ThemeDto createOrUpdateTheme(Long id, CreateThemeDto createThemeDto) {
    Theme theme;
    if (id == null) {
      // Create logic
      theme = new Theme();
    } else {
      // Update logic
      theme = validateThemeId(id);
      utils.validateAdminUser();
    }

    theme.setTitle(createThemeDto.getTitle());
    theme.setDescription(createThemeDto.getDescription());
    theme.setIcon(createThemeDto.getIcon());
    theme.setColor(createThemeDto.getColor());

    Theme savedTheme = themeRepository.save(theme);
    return toDto(savedTheme);
  }

  @Transactional(readOnly = true)
  public ThemeDto getThemeById(Long themeId) {
    Theme theme = validateThemeId(themeId);
    Set<Long> articleIds = themeRepository.findArticleIdsByThemeId(themeId);
    return toDto(theme, articleIds);
  }

  @Transactional(readOnly = true)
  public Set<ThemeDto> getAllThemes() {
    List<Theme> themes = themeRepository.findAll();
    return themes
      .stream()
      .map(theme -> {
        Set<Long> articleIds = themeRepository.findArticleIdsByThemeId(
          theme.getId()
        );
        return toDto(theme, articleIds);
      })
      .collect(Collectors.toSet());
  }

  @Transactional
  public void deleteTheme(Long id) {
    Theme theme = validateThemeId(id);
    utils.validateAdminUser(); // Only admin can delete themes
    themeRepository.delete(theme);
  }

  private Theme validateThemeId(Long id) {
    return themeRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("Theme not found with id: " + id)
      );
  }

  private ThemeDto toDto(Theme theme) {
    return toDto(theme, Collections.emptySet());
  }

  private ThemeDto toDto(Theme theme, Set<Long> articleIds) {
    return ThemeDto
      .builder()
      .id(theme.getId())
      .title(theme.getTitle())
      .description(theme.getDescription())
      .icon(theme.getIcon())
      .color(theme.getColor())
      .articleIds(articleIds)
      .build();
  }
}
