import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDocumentkop } from '../documentkop.model';
import { DocumentkopService } from '../service/documentkop.service';

@Component({
  standalone: true,
  templateUrl: './documentkop-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DocumentkopDeleteDialogComponent {
  documentkop?: IDocumentkop;

  protected documentkopService = inject(DocumentkopService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentkopService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
