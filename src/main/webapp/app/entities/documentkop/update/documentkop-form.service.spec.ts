import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../documentkop.test-samples';

import { DocumentkopFormService } from './documentkop-form.service';

describe('Documentkop Form Service', () => {
  let service: DocumentkopFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentkopFormService);
  });

  describe('Service methods', () => {
    describe('createDocumentkopFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocumentkopFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bedrijfsnummer: expect.any(Object),
            documentNrBoekhoudingsdocument: expect.any(Object),
            boekjaar: expect.any(Object),
            documentsoort: expect.any(Object),
            documentdatum: expect.any(Object),
            boekingsdatum: expect.any(Object),
            boekmaand: expect.any(Object),
            invoerdag: expect.any(Object),
            invoertijd: expect.any(Object),
          }),
        );
      });

      it('passing IDocumentkop should create a new form with FormGroup', () => {
        const formGroup = service.createDocumentkopFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bedrijfsnummer: expect.any(Object),
            documentNrBoekhoudingsdocument: expect.any(Object),
            boekjaar: expect.any(Object),
            documentsoort: expect.any(Object),
            documentdatum: expect.any(Object),
            boekingsdatum: expect.any(Object),
            boekmaand: expect.any(Object),
            invoerdag: expect.any(Object),
            invoertijd: expect.any(Object),
          }),
        );
      });
    });

    describe('getDocumentkop', () => {
      it('should return NewDocumentkop for default Documentkop initial value', () => {
        const formGroup = service.createDocumentkopFormGroup(sampleWithNewData);

        const documentkop = service.getDocumentkop(formGroup) as any;

        expect(documentkop).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocumentkop for empty Documentkop initial value', () => {
        const formGroup = service.createDocumentkopFormGroup();

        const documentkop = service.getDocumentkop(formGroup) as any;

        expect(documentkop).toMatchObject({});
      });

      it('should return IDocumentkop', () => {
        const formGroup = service.createDocumentkopFormGroup(sampleWithRequiredData);

        const documentkop = service.getDocumentkop(formGroup) as any;

        expect(documentkop).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocumentkop should not enable id FormControl', () => {
        const formGroup = service.createDocumentkopFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocumentkop should disable id FormControl', () => {
        const formGroup = service.createDocumentkopFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
