import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sort-form',
  templateUrl: './sort-form.component.html',
  styleUrls: ['./sort-form.component.scss'],
  standalone: true,
  imports: [CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatOptionModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
  ],
})
export class SortFormComponent {
  @Input() options: { value: string; label: string; icon?: string }[] = [];
  @Output() sorted = new EventEmitter<string>();

  sortForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.sortForm = this.fb.group({
      sortBy: [''],
    });
  }

  ngOnInit(): void {
    if (this.options.length > 0) {
      this.sortForm.controls['sortBy'].setValue(this.options[0].value);
    }
  }

  onSortChange(): void {
    this.sorted.emit(this.sortForm.get('sortBy')?.value);
  }
}
