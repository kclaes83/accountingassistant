import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../documentsegment.test-samples';

import { DocumentsegmentFormService } from './documentsegment-form.service';

describe('Documentsegment Form Service', () => {
  let service: DocumentsegmentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentsegmentFormService);
  });

  describe('Service methods', () => {
    describe('createDocumentsegmentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocumentsegmentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bedrijfsnummer: expect.any(Object),
            documentNrBoekhoudingsdocument: expect.any(Object),
            boekjaar: expect.any(Object),
            boekingsregelNrBoekhoudingsdocument: expect.any(Object),
            boekingsregelIdentificatie: expect.any(Object),
            vereffeningsdatum: expect.any(Object),
            invoerdatumVereffening: expect.any(Object),
            vereffeningsdocumentNr: expect.any(Object),
            boekingssleutel: expect.any(Object),
            documentkop: expect.any(Object),
          }),
        );
      });

      it('passing IDocumentsegment should create a new form with FormGroup', () => {
        const formGroup = service.createDocumentsegmentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bedrijfsnummer: expect.any(Object),
            documentNrBoekhoudingsdocument: expect.any(Object),
            boekjaar: expect.any(Object),
            boekingsregelNrBoekhoudingsdocument: expect.any(Object),
            boekingsregelIdentificatie: expect.any(Object),
            vereffeningsdatum: expect.any(Object),
            invoerdatumVereffening: expect.any(Object),
            vereffeningsdocumentNr: expect.any(Object),
            boekingssleutel: expect.any(Object),
            documentkop: expect.any(Object),
          }),
        );
      });
    });

    describe('getDocumentsegment', () => {
      it('should return NewDocumentsegment for default Documentsegment initial value', () => {
        const formGroup = service.createDocumentsegmentFormGroup(sampleWithNewData);

        const documentsegment = service.getDocumentsegment(formGroup) as any;

        expect(documentsegment).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocumentsegment for empty Documentsegment initial value', () => {
        const formGroup = service.createDocumentsegmentFormGroup();

        const documentsegment = service.getDocumentsegment(formGroup) as any;

        expect(documentsegment).toMatchObject({});
      });

      it('should return IDocumentsegment', () => {
        const formGroup = service.createDocumentsegmentFormGroup(sampleWithRequiredData);

        const documentsegment = service.getDocumentsegment(formGroup) as any;

        expect(documentsegment).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocumentsegment should not enable id FormControl', () => {
        const formGroup = service.createDocumentsegmentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocumentsegment should disable id FormControl', () => {
        const formGroup = service.createDocumentsegmentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
