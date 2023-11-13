import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICodeTranslation } from '../code-translation.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../code-translation.test-samples';

import { CodeTranslationService } from './code-translation.service';

const requireRestSample: ICodeTranslation = {
  ...sampleWithRequiredData,
};

describe('CodeTranslation Service', () => {
  let service: CodeTranslationService;
  let httpMock: HttpTestingController;
  let expectedResult: ICodeTranslation | ICodeTranslation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CodeTranslationService);
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

    it('should create a CodeTranslation', () => {
      const codeTranslation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(codeTranslation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CodeTranslation', () => {
      const codeTranslation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(codeTranslation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CodeTranslation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CodeTranslation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CodeTranslation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCodeTranslationToCollectionIfMissing', () => {
      it('should add a CodeTranslation to an empty array', () => {
        const codeTranslation: ICodeTranslation = sampleWithRequiredData;
        expectedResult = service.addCodeTranslationToCollectionIfMissing([], codeTranslation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(codeTranslation);
      });

      it('should not add a CodeTranslation to an array that contains it', () => {
        const codeTranslation: ICodeTranslation = sampleWithRequiredData;
        const codeTranslationCollection: ICodeTranslation[] = [
          {
            ...codeTranslation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCodeTranslationToCollectionIfMissing(codeTranslationCollection, codeTranslation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CodeTranslation to an array that doesn't contain it", () => {
        const codeTranslation: ICodeTranslation = sampleWithRequiredData;
        const codeTranslationCollection: ICodeTranslation[] = [sampleWithPartialData];
        expectedResult = service.addCodeTranslationToCollectionIfMissing(codeTranslationCollection, codeTranslation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(codeTranslation);
      });

      it('should add only unique CodeTranslation to an array', () => {
        const codeTranslationArray: ICodeTranslation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const codeTranslationCollection: ICodeTranslation[] = [sampleWithRequiredData];
        expectedResult = service.addCodeTranslationToCollectionIfMissing(codeTranslationCollection, ...codeTranslationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const codeTranslation: ICodeTranslation = sampleWithRequiredData;
        const codeTranslation2: ICodeTranslation = sampleWithPartialData;
        expectedResult = service.addCodeTranslationToCollectionIfMissing([], codeTranslation, codeTranslation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(codeTranslation);
        expect(expectedResult).toContain(codeTranslation2);
      });

      it('should accept null and undefined values', () => {
        const codeTranslation: ICodeTranslation = sampleWithRequiredData;
        expectedResult = service.addCodeTranslationToCollectionIfMissing([], null, codeTranslation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(codeTranslation);
      });

      it('should return initial array if no CodeTranslation is added', () => {
        const codeTranslationCollection: ICodeTranslation[] = [sampleWithRequiredData];
        expectedResult = service.addCodeTranslationToCollectionIfMissing(codeTranslationCollection, undefined, null);
        expect(expectedResult).toEqual(codeTranslationCollection);
      });
    });

    describe('compareCodeTranslation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCodeTranslation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCodeTranslation(entity1, entity2);
        const compareResult2 = service.compareCodeTranslation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCodeTranslation(entity1, entity2);
        const compareResult2 = service.compareCodeTranslation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCodeTranslation(entity1, entity2);
        const compareResult2 = service.compareCodeTranslation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
