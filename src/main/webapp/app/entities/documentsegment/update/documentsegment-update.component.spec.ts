import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IDocumentkop } from 'app/entities/documentkop/documentkop.model';
import { DocumentkopService } from 'app/entities/documentkop/service/documentkop.service';
import { DocumentsegmentService } from '../service/documentsegment.service';
import { IDocumentsegment } from '../documentsegment.model';
import { DocumentsegmentFormService } from './documentsegment-form.service';

import { DocumentsegmentUpdateComponent } from './documentsegment-update.component';

describe('Documentsegment Management Update Component', () => {
  let comp: DocumentsegmentUpdateComponent;
  let fixture: ComponentFixture<DocumentsegmentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentsegmentFormService: DocumentsegmentFormService;
  let documentsegmentService: DocumentsegmentService;
  let documentkopService: DocumentkopService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DocumentsegmentUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DocumentsegmentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentsegmentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentsegmentFormService = TestBed.inject(DocumentsegmentFormService);
    documentsegmentService = TestBed.inject(DocumentsegmentService);
    documentkopService = TestBed.inject(DocumentkopService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Documentkop query and add missing value', () => {
      const documentsegment: IDocumentsegment = { id: 456 };
      const documentkop: IDocumentkop = { id: 17557 };
      documentsegment.documentkop = documentkop;

      const documentkopCollection: IDocumentkop[] = [{ id: 23218 }];
      jest.spyOn(documentkopService, 'query').mockReturnValue(of(new HttpResponse({ body: documentkopCollection })));
      const additionalDocumentkops = [documentkop];
      const expectedCollection: IDocumentkop[] = [...additionalDocumentkops, ...documentkopCollection];
      jest.spyOn(documentkopService, 'addDocumentkopToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documentsegment });
      comp.ngOnInit();

      expect(documentkopService.query).toHaveBeenCalled();
      expect(documentkopService.addDocumentkopToCollectionIfMissing).toHaveBeenCalledWith(
        documentkopCollection,
        ...additionalDocumentkops.map(expect.objectContaining),
      );
      expect(comp.documentkopsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const documentsegment: IDocumentsegment = { id: 456 };
      const documentkop: IDocumentkop = { id: 5878 };
      documentsegment.documentkop = documentkop;

      activatedRoute.data = of({ documentsegment });
      comp.ngOnInit();

      expect(comp.documentkopsSharedCollection).toContain(documentkop);
      expect(comp.documentsegment).toEqual(documentsegment);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentsegment>>();
      const documentsegment = { id: 123 };
      jest.spyOn(documentsegmentFormService, 'getDocumentsegment').mockReturnValue(documentsegment);
      jest.spyOn(documentsegmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentsegment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentsegment }));
      saveSubject.complete();

      // THEN
      expect(documentsegmentFormService.getDocumentsegment).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentsegmentService.update).toHaveBeenCalledWith(expect.objectContaining(documentsegment));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentsegment>>();
      const documentsegment = { id: 123 };
      jest.spyOn(documentsegmentFormService, 'getDocumentsegment').mockReturnValue({ id: null });
      jest.spyOn(documentsegmentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentsegment: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentsegment }));
      saveSubject.complete();

      // THEN
      expect(documentsegmentFormService.getDocumentsegment).toHaveBeenCalled();
      expect(documentsegmentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentsegment>>();
      const documentsegment = { id: 123 };
      jest.spyOn(documentsegmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentsegment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentsegmentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDocumentkop', () => {
      it('Should forward to documentkopService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(documentkopService, 'compareDocumentkop');
        comp.compareDocumentkop(entity, entity2);
        expect(documentkopService.compareDocumentkop).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
