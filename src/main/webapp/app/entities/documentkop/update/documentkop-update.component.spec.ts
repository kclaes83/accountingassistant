import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { DocumentkopService } from '../service/documentkop.service';
import { IDocumentkop } from '../documentkop.model';
import { DocumentkopFormService } from './documentkop-form.service';

import { DocumentkopUpdateComponent } from './documentkop-update.component';

describe('Documentkop Management Update Component', () => {
  let comp: DocumentkopUpdateComponent;
  let fixture: ComponentFixture<DocumentkopUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentkopFormService: DocumentkopFormService;
  let documentkopService: DocumentkopService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DocumentkopUpdateComponent],
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
      .overrideTemplate(DocumentkopUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentkopUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentkopFormService = TestBed.inject(DocumentkopFormService);
    documentkopService = TestBed.inject(DocumentkopService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const documentkop: IDocumentkop = { id: 456 };

      activatedRoute.data = of({ documentkop });
      comp.ngOnInit();

      expect(comp.documentkop).toEqual(documentkop);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentkop>>();
      const documentkop = { id: 123 };
      jest.spyOn(documentkopFormService, 'getDocumentkop').mockReturnValue(documentkop);
      jest.spyOn(documentkopService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentkop });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentkop }));
      saveSubject.complete();

      // THEN
      expect(documentkopFormService.getDocumentkop).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentkopService.update).toHaveBeenCalledWith(expect.objectContaining(documentkop));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentkop>>();
      const documentkop = { id: 123 };
      jest.spyOn(documentkopFormService, 'getDocumentkop').mockReturnValue({ id: null });
      jest.spyOn(documentkopService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentkop: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentkop }));
      saveSubject.complete();

      // THEN
      expect(documentkopFormService.getDocumentkop).toHaveBeenCalled();
      expect(documentkopService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentkop>>();
      const documentkop = { id: 123 };
      jest.spyOn(documentkopService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentkop });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentkopService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
