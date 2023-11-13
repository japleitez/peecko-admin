import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VideoStatService } from '../service/video-stat.service';
import { IVideoStat } from '../video-stat.model';
import { VideoStatFormService } from './video-stat-form.service';

import { VideoStatUpdateComponent } from './video-stat-update.component';

describe('VideoStat Management Update Component', () => {
  let comp: VideoStatUpdateComponent;
  let fixture: ComponentFixture<VideoStatUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let videoStatFormService: VideoStatFormService;
  let videoStatService: VideoStatService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), VideoStatUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(VideoStatUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VideoStatUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    videoStatFormService = TestBed.inject(VideoStatFormService);
    videoStatService = TestBed.inject(VideoStatService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const videoStat: IVideoStat = { id: 456 };

      activatedRoute.data = of({ videoStat });
      comp.ngOnInit();

      expect(comp.videoStat).toEqual(videoStat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideoStat>>();
      const videoStat = { id: 123 };
      jest.spyOn(videoStatFormService, 'getVideoStat').mockReturnValue(videoStat);
      jest.spyOn(videoStatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ videoStat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: videoStat }));
      saveSubject.complete();

      // THEN
      expect(videoStatFormService.getVideoStat).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(videoStatService.update).toHaveBeenCalledWith(expect.objectContaining(videoStat));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideoStat>>();
      const videoStat = { id: 123 };
      jest.spyOn(videoStatFormService, 'getVideoStat').mockReturnValue({ id: null });
      jest.spyOn(videoStatService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ videoStat: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: videoStat }));
      saveSubject.complete();

      // THEN
      expect(videoStatFormService.getVideoStat).toHaveBeenCalled();
      expect(videoStatService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideoStat>>();
      const videoStat = { id: 123 };
      jest.spyOn(videoStatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ videoStat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(videoStatService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
