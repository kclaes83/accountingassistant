import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDocumentsegment } from '../documentsegment.model';
import { DocumentsegmentService } from '../service/documentsegment.service';

@Component({
  standalone: true,
  templateUrl: './documentsegment-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DocumentsegmentDeleteDialogComponent {
  documentsegment?: IDocumentsegment;

  protected documentsegmentService = inject(DocumentsegmentService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentsegmentService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
