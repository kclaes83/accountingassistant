import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDocumentkop } from 'app/entities/documentkop/documentkop.model';
import { DocumentkopService } from 'app/entities/documentkop/service/documentkop.service';
import { IDocumentsegment } from '../documentsegment.model';
import { DocumentsegmentService } from '../service/documentsegment.service';
import { DocumentsegmentFormGroup, DocumentsegmentFormService } from './documentsegment-form.service';

@Component({
  standalone: true,
  selector: 'jhi-documentsegment-update',
  templateUrl: './documentsegment-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DocumentsegmentUpdateComponent implements OnInit {
  isSaving = false;
  documentsegment: IDocumentsegment | null = null;

  documentkopsSharedCollection: IDocumentkop[] = [];

  protected documentsegmentService = inject(DocumentsegmentService);
  protected documentsegmentFormService = inject(DocumentsegmentFormService);
  protected documentkopService = inject(DocumentkopService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DocumentsegmentFormGroup = this.documentsegmentFormService.createDocumentsegmentFormGroup();

  compareDocumentkop = (o1: IDocumentkop | null, o2: IDocumentkop | null): boolean => this.documentkopService.compareDocumentkop(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentsegment }) => {
      this.documentsegment = documentsegment;
      if (documentsegment) {
        this.updateForm(documentsegment);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentsegment = this.documentsegmentFormService.getDocumentsegment(this.editForm);
    if (documentsegment.id !== null) {
      this.subscribeToSaveResponse(this.documentsegmentService.update(documentsegment));
    } else {
      this.subscribeToSaveResponse(this.documentsegmentService.create(documentsegment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentsegment>>): void {
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

  protected updateForm(documentsegment: IDocumentsegment): void {
    this.documentsegment = documentsegment;
    this.documentsegmentFormService.resetForm(this.editForm, documentsegment);

    this.documentkopsSharedCollection = this.documentkopService.addDocumentkopToCollectionIfMissing<IDocumentkop>(
      this.documentkopsSharedCollection,
      documentsegment.documentkop,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.documentkopService
      .query()
      .pipe(map((res: HttpResponse<IDocumentkop[]>) => res.body ?? []))
      .pipe(
        map((documentkops: IDocumentkop[]) =>
          this.documentkopService.addDocumentkopToCollectionIfMissing<IDocumentkop>(documentkops, this.documentsegment?.documentkop),
        ),
      )
      .subscribe((documentkops: IDocumentkop[]) => (this.documentkopsSharedCollection = documentkops));
  }
}
