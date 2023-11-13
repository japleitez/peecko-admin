import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILikedVideo } from '../liked-video.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../liked-video.test-samples';

import { LikedVideoService, RestLikedVideo } from './liked-video.service';

const requireRestSample: RestLikedVideo = {
  ...sampleWithRequiredData,
  created: sampleWithRequiredData.created?.toJSON(),
};

describe('LikedVideo Service', () => {
  let service: LikedVideoService;
  let httpMock: HttpTestingController;
  let expectedResult: ILikedVideo | ILikedVideo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LikedVideoService);
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

    it('should create a LikedVideo', () => {
      const likedVideo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(likedVideo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LikedVideo', () => {
      const likedVideo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(likedVideo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LikedVideo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LikedVideo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LikedVideo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLikedVideoToCollectionIfMissing', () => {
      it('should add a LikedVideo to an empty array', () => {
        const likedVideo: ILikedVideo = sampleWithRequiredData;
        expectedResult = service.addLikedVideoToCollectionIfMissing([], likedVideo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(likedVideo);
      });

      it('should not add a LikedVideo to an array that contains it', () => {
        const likedVideo: ILikedVideo = sampleWithRequiredData;
        const likedVideoCollection: ILikedVideo[] = [
          {
            ...likedVideo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLikedVideoToCollectionIfMissing(likedVideoCollection, likedVideo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LikedVideo to an array that doesn't contain it", () => {
        const likedVideo: ILikedVideo = sampleWithRequiredData;
        const likedVideoCollection: ILikedVideo[] = [sampleWithPartialData];
        expectedResult = service.addLikedVideoToCollectionIfMissing(likedVideoCollection, likedVideo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(likedVideo);
      });

      it('should add only unique LikedVideo to an array', () => {
        const likedVideoArray: ILikedVideo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const likedVideoCollection: ILikedVideo[] = [sampleWithRequiredData];
        expectedResult = service.addLikedVideoToCollectionIfMissing(likedVideoCollection, ...likedVideoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const likedVideo: ILikedVideo = sampleWithRequiredData;
        const likedVideo2: ILikedVideo = sampleWithPartialData;
        expectedResult = service.addLikedVideoToCollectionIfMissing([], likedVideo, likedVideo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(likedVideo);
        expect(expectedResult).toContain(likedVideo2);
      });

      it('should accept null and undefined values', () => {
        const likedVideo: ILikedVideo = sampleWithRequiredData;
        expectedResult = service.addLikedVideoToCollectionIfMissing([], null, likedVideo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(likedVideo);
      });

      it('should return initial array if no LikedVideo is added', () => {
        const likedVideoCollection: ILikedVideo[] = [sampleWithRequiredData];
        expectedResult = service.addLikedVideoToCollectionIfMissing(likedVideoCollection, undefined, null);
        expect(expectedResult).toEqual(likedVideoCollection);
      });
    });

    describe('compareLikedVideo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLikedVideo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLikedVideo(entity1, entity2);
        const compareResult2 = service.compareLikedVideo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLikedVideo(entity1, entity2);
        const compareResult2 = service.compareLikedVideo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLikedVideo(entity1, entity2);
        const compareResult2 = service.compareLikedVideo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
