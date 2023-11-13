import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../liked-video.test-samples';

import { LikedVideoFormService } from './liked-video-form.service';

describe('LikedVideo Form Service', () => {
  let service: LikedVideoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LikedVideoFormService);
  });

  describe('Service methods', () => {
    describe('createLikedVideoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLikedVideoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            videoId: expect.any(Object),
            personId: expect.any(Object),
            coachId: expect.any(Object),
            created: expect.any(Object),
          }),
        );
      });

      it('passing ILikedVideo should create a new form with FormGroup', () => {
        const formGroup = service.createLikedVideoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            videoId: expect.any(Object),
            personId: expect.any(Object),
            coachId: expect.any(Object),
            created: expect.any(Object),
          }),
        );
      });
    });

    describe('getLikedVideo', () => {
      it('should return NewLikedVideo for default LikedVideo initial value', () => {
        const formGroup = service.createLikedVideoFormGroup(sampleWithNewData);

        const likedVideo = service.getLikedVideo(formGroup) as any;

        expect(likedVideo).toMatchObject(sampleWithNewData);
      });

      it('should return NewLikedVideo for empty LikedVideo initial value', () => {
        const formGroup = service.createLikedVideoFormGroup();

        const likedVideo = service.getLikedVideo(formGroup) as any;

        expect(likedVideo).toMatchObject({});
      });

      it('should return ILikedVideo', () => {
        const formGroup = service.createLikedVideoFormGroup(sampleWithRequiredData);

        const likedVideo = service.getLikedVideo(formGroup) as any;

        expect(likedVideo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILikedVideo should not enable id FormControl', () => {
        const formGroup = service.createLikedVideoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLikedVideo should disable id FormControl', () => {
        const formGroup = service.createLikedVideoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
