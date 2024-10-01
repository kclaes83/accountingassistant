import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DocumentSoort } from 'app/entities/enumerations/document-soort.model';
import { Maand } from 'app/entities/enumerations/maand.model';
import { IDocumentkop } from '../documentkop.model';
import { DocumentkopService } from '../service/documentkop.service';
import { DocumentkopFormGroup, DocumentkopFormService } from './documentkop-form.service';

@Component({
  standalone: true,
  selector: 'jhi-documentkop-update',
  templateUrl: './documentkop-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DocumentkopUpdateComponent implements OnInit {
  isSaving = false;
  documentkop: IDocumentkop | null = null;
  documentSoortValues = Object.keys(DocumentSoort);
  maandValues = Object.keys(Maand);

  protected documentkopService = inject(DocumentkopService);
  protected documentkopFormService = inject(DocumentkopFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DocumentkopFormGroup = this.documentkopFormService.createDocumentkopFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentkop }) => {
      this.documentkop = documentkop;
      if (documentkop) {
        this.updateForm(documentkop);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentkop = this.documentkopFormService.getDocumentkop(this.editForm);
    if (documentkop.id !== null) {
      this.subscribeToSaveResponse(this.documentkopService.update(documentkop));
    } else {
      this.subscribeToSaveResponse(this.documentkopService.create(documentkop));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentkop>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(documentkop: IDocumentkop): void {
    this.documentkop = documentkop;
    this.documentkopFormService.resetForm(this.editForm, documentkop);
  }
}
