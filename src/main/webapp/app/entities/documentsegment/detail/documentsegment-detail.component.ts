import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IDocumentsegment } from '../documentsegment.model';

@Component({
  standalone: true,
  selector: 'jhi-documentsegment-detail',
  templateUrl: './documentsegment-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DocumentsegmentDetailComponent {
  documentsegment = input<IDocumentsegment | null>(null);

  previousState(): void {
    window.history.back();
  }
}
