import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IApsAccount } from '../aps-account.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../aps-account.test-samples';

import { ApsAccountService, RestApsAccount } from './aps-account.service';

const requireRestSample: RestApsAccount = {
  ...sampleWithRequiredData,
  created: sampleWithRequiredData.created?.toJSON(),
  updated: sampleWithRequiredData.updated?.toJSON(),
};

describe('ApsAccount Service', () => {
  let service: ApsAccountService;
  let httpMock: HttpTestingController;
  let expectedResult: IApsAccount | IApsAccount[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ApsAccountService);
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

    it('should create a ApsAccount', () => {
      const apsAccount = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(apsAccount).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ApsAccount', () => {
      const apsAccount = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(apsAccount).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ApsAccount', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ApsAccount', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ApsAccount', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addApsAccountToCollectionIfMissing', () => {
      it('should add a ApsAccount to an empty array', () => {
        const apsAccount: IApsAccount = sampleWithRequiredData;
        expectedResult = service.addApsAccountToCollectionIfMissing([], apsAccount);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apsAccount);
      });

      it('should not add a ApsAccount to an array that contains it', () => {
        const apsAccount: IApsAccount = sampleWithRequiredData;
        const apsAccountCollection: IApsAccount[] = [
          {
            ...apsAccount,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addApsAccountToCollectionIfMissing(apsAccountCollection, apsAccount);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ApsAccount to an array that doesn't contain it", () => {
        const apsAccount: IApsAccount = sampleWithRequiredData;
        const apsAccountCollection: IApsAccount[] = [sampleWithPartialData];
        expectedResult = service.addApsAccountToCollectionIfMissing(apsAccountCollection, apsAccount);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apsAccount);
      });

      it('should add only unique ApsAccount to an array', () => {
        const apsAccountArray: IApsAccount[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const apsAccountCollection: IApsAccount[] = [sampleWithRequiredData];
        expectedResult = service.addApsAccountToCollectionIfMissing(apsAccountCollection, ...apsAccountArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const apsAccount: IApsAccount = sampleWithRequiredData;
        const apsAccount2: IApsAccount = sampleWithPartialData;
        expectedResult = service.addApsAccountToCollectionIfMissing([], apsAccount, apsAccount2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apsAccount);
        expect(expectedResult).toContain(apsAccount2);
      });

      it('should accept null and undefined values', () => {
        const apsAccount: IApsAccount = sampleWithRequiredData;
        expectedResult = service.addApsAccountToCollectionIfMissing([], null, apsAccount, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apsAccount);
      });

      it('should return initial array if no ApsAccount is added', () => {
        const apsAccountCollection: IApsAccount[] = [sampleWithRequiredData];
        expectedResult = service.addApsAccountToCollectionIfMissing(apsAccountCollection, undefined, null);
        expect(expectedResult).toEqual(apsAccountCollection);
      });
    });

    describe('compareApsAccount', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareApsAccount(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareApsAccount(entity1, entity2);
        const compareResult2 = service.compareApsAccount(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareApsAccount(entity1, entity2);
        const compareResult2 = service.compareApsAccount(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareApsAccount(entity1, entity2);
        const compareResult2 = service.compareApsAccount(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
