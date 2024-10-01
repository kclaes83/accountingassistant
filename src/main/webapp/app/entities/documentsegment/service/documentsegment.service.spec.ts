import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDocumentsegment } from '../documentsegment.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../documentsegment.test-samples';

import { DocumentsegmentService, RestDocumentsegment } from './documentsegment.service';

const requireRestSample: RestDocumentsegment = {
  ...sampleWithRequiredData,
  vereffeningsdatum: sampleWithRequiredData.vereffeningsdatum?.toJSON(),
  invoerdatumVereffening: sampleWithRequiredData.invoerdatumVereffening?.toJSON(),
};

describe('Documentsegment Service', () => {
  let service: DocumentsegmentService;
  let httpMock: HttpTestingController;
  let expectedResult: IDocumentsegment | IDocumentsegment[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DocumentsegmentService);
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

    it('should create a Documentsegment', () => {
      const documentsegment = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(documentsegment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Documentsegment', () => {
      const documentsegment = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(documentsegment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Documentsegment', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Documentsegment', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Documentsegment', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDocumentsegmentToCollectionIfMissing', () => {
      it('should add a Documentsegment to an empty array', () => {
        const documentsegment: IDocumentsegment = sampleWithRequiredData;
        expectedResult = service.addDocumentsegmentToCollectionIfMissing([], documentsegment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentsegment);
      });

      it('should not add a Documentsegment to an array that contains it', () => {
        const documentsegment: IDocumentsegment = sampleWithRequiredData;
        const documentsegmentCollection: IDocumentsegment[] = [
          {
            ...documentsegment,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDocumentsegmentToCollectionIfMissing(documentsegmentCollection, documentsegment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Documentsegment to an array that doesn't contain it", () => {
        const documentsegment: IDocumentsegment = sampleWithRequiredData;
        const documentsegmentCollection: IDocumentsegment[] = [sampleWithPartialData];
        expectedResult = service.addDocumentsegmentToCollectionIfMissing(documentsegmentCollection, documentsegment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentsegment);
      });

      it('should add only unique Documentsegment to an array', () => {
        const documentsegmentArray: IDocumentsegment[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const documentsegmentCollection: IDocumentsegment[] = [sampleWithRequiredData];
        expectedResult = service.addDocumentsegmentToCollectionIfMissing(documentsegmentCollection, ...documentsegmentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const documentsegment: IDocumentsegment = sampleWithRequiredData;
        const documentsegment2: IDocumentsegment = sampleWithPartialData;
        expectedResult = service.addDocumentsegmentToCollectionIfMissing([], documentsegment, documentsegment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentsegment);
        expect(expectedResult).toContain(documentsegment2);
      });

      it('should accept null and undefined values', () => {
        const documentsegment: IDocumentsegment = sampleWithRequiredData;
        expectedResult = service.addDocumentsegmentToCollectionIfMissing([], null, documentsegment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentsegment);
      });

      it('should return initial array if no Documentsegment is added', () => {
        const documentsegmentCollection: IDocumentsegment[] = [sampleWithRequiredData];
        expectedResult = service.addDocumentsegmentToCollectionIfMissing(documentsegmentCollection, undefined, null);
        expect(expectedResult).toEqual(documentsegmentCollection);
      });
    });

    describe('compareDocumentsegment', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDocumentsegment(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDocumentsegment(entity1, entity2);
        const compareResult2 = service.compareDocumentsegment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDocumentsegment(entity1, entity2);
        const compareResult2 = service.compareDocumentsegment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDocumentsegment(entity1, entity2);
        const compareResult2 = service.compareDocumentsegment(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
