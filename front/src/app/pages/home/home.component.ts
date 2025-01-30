import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  standalone: true,
  imports: [CommonModule, MatButtonModule],
})
export class HomeComponent implements OnInit{
  constructor(private router: Router) {}

  ngOnInit(): void {}
  
  routing(param: string) {
    this.router.navigate([param]);
  }
}
