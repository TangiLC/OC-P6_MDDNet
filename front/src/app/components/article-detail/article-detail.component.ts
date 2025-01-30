import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { ThemesService } from '../../services/theme.service';
import { map, Observable, of } from 'rxjs';
import { Theme } from '../../interfaces/theme.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article-detail',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatDividerModule, MatIconModule],
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss'],
})
export class ArticleDetailComponent {
  @Input() article: {
    id: number;
    title: string;
    content: string;
    createdAt: string;
    updatedAt: string;
    authorUsername: string;
    authorPicture: string;
    themeIds: number[];
  } | null = null;
  @Input() isFullView: Boolean | undefined;

  themes$: Observable<Theme[]> | null = null;
  constructor(private themesService: ThemesService, private router: Router) {}

  ngOnInit(): void {
    this.themes$ = this.themesService.themes$;
  }

  getThemeTitle(themeId: number, themes: Theme[]): string {
    return themes.find((theme) => theme.id === themeId)?.title ?? 'Inconnu';
  }
  getThemeColor(themeId: number, themes: Theme[]): string {
    return '#' + (themes.find((theme) => theme.id === themeId)?.color ?? 'fff');
  }
  getThemeIcon(themeId: number, themes: Theme[]): string {
    return (
      (themes.find((theme) => theme.id === themeId)?.icon ?? 'themeA') + '.png'
    );
  }

  navigateToTheme(themeId: number): void {
    this.router.navigate(['/theme', themeId]);
  }
}
