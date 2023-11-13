import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../video-stat.test-samples';

import { VideoStatFormService } from './video-stat-form.service';

describe('VideoStat Form Service', () => {
  let service: VideoStatFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VideoStatFormService);
  });

  describe('Service methods', () => {
    describe('createVideoStatFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVideoStatFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            videoId: expect.any(Object),
            categoryId: expect.any(Object),
            coachId: expect.any(Object),
            liked: expect.any(Object),
            viewed: expect.any(Object),
          }),
        );
      });

      it('passing IVideoStat should create a new form with FormGroup', () => {
        const formGroup = service.createVideoStatFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            videoId: expect.any(Object),
            categoryId: expect.any(Object),
            coachId: expect.any(Object),
            liked: expect.any(Object),
            viewed: expect.any(Object),
          }),
        );
      });
    });

    describe('getVideoStat', () => {
      it('should return NewVideoStat for default VideoStat initial value', () => {
        const formGroup = service.createVideoStatFormGroup(sampleWithNewData);

        const videoStat = service.getVideoStat(formGroup) as any;

        expect(videoStat).toMatchObject(sampleWithNewData);
      });

      it('should return NewVideoStat for empty VideoStat initial value', () => {
        const formGroup = service.createVideoStatFormGroup();

        const videoStat = service.getVideoStat(formGroup) as any;

        expect(videoStat).toMatchObject({});
      });

      it('should return IVideoStat', () => {
        const formGroup = service.createVideoStatFormGroup(sampleWithRequiredData);

        const videoStat = service.getVideoStat(formGroup) as any;

        expect(videoStat).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVideoStat should not enable id FormControl', () => {
        const formGroup = service.createVideoStatFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVideoStat should disable id FormControl', () => {
        const formGroup = service.createVideoStatFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
