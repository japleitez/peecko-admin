import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISecuredRequest } from '../secured-request.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../secured-request.test-samples';

import { SecuredRequestService, RestSecuredRequest } from './secured-request.service';

const requireRestSample: RestSecuredRequest = {
  ...sampleWithRequiredData,
  created: sampleWithRequiredData.created?.toJSON(),
  expires: sampleWithRequiredData.expires?.toJSON(),
};

describe('SecuredRequest Service', () => {
  let service: SecuredRequestService;
  let httpMock: HttpTestingController;
  let expectedResult: ISecuredRequest | ISecuredRequest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SecuredRequestService);
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

    it('should create a SecuredRequest', () => {
      const securedRequest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(securedRequest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SecuredRequest', () => {
      const securedRequest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(securedRequest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SecuredRequest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SecuredRequest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SecuredRequest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSecuredRequestToCollectionIfMissing', () => {
      it('should add a SecuredRequest to an empty array', () => {
        const securedRequest: ISecuredRequest = sampleWithRequiredData;
        expectedResult = service.addSecuredRequestToCollectionIfMissing([], securedRequest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securedRequest);
      });

      it('should not add a SecuredRequest to an array that contains it', () => {
        const securedRequest: ISecuredRequest = sampleWithRequiredData;
        const securedRequestCollection: ISecuredRequest[] = [
          {
            ...securedRequest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSecuredRequestToCollectionIfMissing(securedRequestCollection, securedRequest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SecuredRequest to an array that doesn't contain it", () => {
        const securedRequest: ISecuredRequest = sampleWithRequiredData;
        const securedRequestCollection: ISecuredRequest[] = [sampleWithPartialData];
        expectedResult = service.addSecuredRequestToCollectionIfMissing(securedRequestCollection, securedRequest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securedRequest);
      });

      it('should add only unique SecuredRequest to an array', () => {
        const securedRequestArray: ISecuredRequest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const securedRequestCollection: ISecuredRequest[] = [sampleWithRequiredData];
        expectedResult = service.addSecuredRequestToCollectionIfMissing(securedRequestCollection, ...securedRequestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const securedRequest: ISecuredRequest = sampleWithRequiredData;
        const securedRequest2: ISecuredRequest = sampleWithPartialData;
        expectedResult = service.addSecuredRequestToCollectionIfMissing([], securedRequest, securedRequest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securedRequest);
        expect(expectedResult).toContain(securedRequest2);
      });

      it('should accept null and undefined values', () => {
        const securedRequest: ISecuredRequest = sampleWithRequiredData;
        expectedResult = service.addSecuredRequestToCollectionIfMissing([], null, securedRequest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securedRequest);
      });

      it('should return initial array if no SecuredRequest is added', () => {
        const securedRequestCollection: ISecuredRequest[] = [sampleWithRequiredData];
        expectedResult = service.addSecuredRequestToCollectionIfMissing(securedRequestCollection, undefined, null);
        expect(expectedResult).toEqual(securedRequestCollection);
      });
    });

    describe('compareSecuredRequest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSecuredRequest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSecuredRequest(entity1, entity2);
        const compareResult2 = service.compareSecuredRequest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSecuredRequest(entity1, entity2);
        const compareResult2 = service.compareSecuredRequest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSecuredRequest(entity1, entity2);
        const compareResult2 = service.compareSecuredRequest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
