import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDocumentkop } from '../documentkop.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../documentkop.test-samples';

import { DocumentkopService, RestDocumentkop } from './documentkop.service';

const requireRestSample: RestDocumentkop = {
  ...sampleWithRequiredData,
  documentdatum: sampleWithRequiredData.documentdatum?.toJSON(),
  boekingsdatum: sampleWithRequiredData.boekingsdatum?.toJSON(),
  invoerdag: sampleWithRequiredData.invoerdag?.toJSON(),
  invoertijd: sampleWithRequiredData.invoertijd?.toJSON(),
};

describe('Documentkop Service', () => {
  let service: DocumentkopService;
  let httpMock: HttpTestingController;
  let expectedResult: IDocumentkop | IDocumentkop[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DocumentkopService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Documentkop', () => {
      const documentkop = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(documentkop).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Documentkop', () => {
      const documentkop = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(documentkop).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Documentkop', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Documentkop', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Documentkop', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDocumentkopToCollectionIfMissing', () => {
      it('should add a Documentkop to an empty array', () => {
        const documentkop: IDocumentkop = sampleWithRequiredData;
        expectedResult = service.addDocumentkopToCollectionIfMissing([], documentkop);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentkop);
      });

      it('should not add a Documentkop to an array that contains it', () => {
        const documentkop: IDocumentkop = sampleWithRequiredData;
        const documentkopCollection: IDocumentkop[] = [
          {
            ...documentkop,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDocumentkopToCollectionIfMissing(documentkopCollection, documentkop);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Documentkop to an array that doesn't contain it", () => {
        const documentkop: IDocumentkop = sampleWithRequiredData;
        const documentkopCollection: IDocumentkop[] = [sampleWithPartialData];
        expectedResult = service.addDocumentkopToCollectionIfMissing(documentkopCollection, documentkop);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentkop);
      });

      it('should add only unique Documentkop to an array', () => {
        const documentkopArray: IDocumentkop[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const documentkopCollection: IDocumentkop[] = [sampleWithRequiredData];
        expectedResult = service.addDocumentkopToCollectionIfMissing(documentkopCollection, ...documentkopArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const documentkop: IDocumentkop = sampleWithRequiredData;
        const documentkop2: IDocumentkop = sampleWithPartialData;
        expectedResult = service.addDocumentkopToCollectionIfMissing([], documentkop, documentkop2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentkop);
        expect(expectedResult).toContain(documentkop2);
      });

      it('should accept null and undefined values', () => {
        const documentkop: IDocumentkop = sampleWithRequiredData;
        expectedResult = service.addDocumentkopToCollectionIfMissing([], null, documentkop, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentkop);
      });

      it('should return initial array if no Documentkop is added', () => {
        const documentkopCollection: IDocumentkop[] = [sampleWithRequiredData];
        expectedResult = service.addDocumentkopToCollectionIfMissing(documentkopCollection, undefined, null);
        expect(expectedResult).toEqual(documentkopCollection);
      });
    });

    describe('compareDocumentkop', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDocumentkop(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDocumentkop(entity1, entity2);
        const compareResult2 = service.compareDocumentkop(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDocumentkop(entity1, entity2);
        const compareResult2 = service.compareDocumentkop(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDocumentkop(entity1, entity2);
        const compareResult2 = service.compareDocumentkop(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
