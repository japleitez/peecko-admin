import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LikedVideoService } from '../service/liked-video.service';
import { ILikedVideo } from '../liked-video.model';
import { LikedVideoFormService } from './liked-video-form.service';

import { LikedVideoUpdateComponent } from './liked-video-update.component';

describe('LikedVideo Management Update Component', () => {
  let comp: LikedVideoUpdateComponent;
  let fixture: ComponentFixture<LikedVideoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let likedVideoFormService: LikedVideoFormService;
  let likedVideoService: LikedVideoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), LikedVideoUpdateComponent],
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
      .overrideTemplate(LikedVideoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LikedVideoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    likedVideoFormService = TestBed.inject(LikedVideoFormService);
    likedVideoService = TestBed.inject(LikedVideoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const likedVideo: ILikedVideo = { id: 456 };

      activatedRoute.data = of({ likedVideo });
      comp.ngOnInit();

      expect(comp.likedVideo).toEqual(likedVideo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILikedVideo>>();
      const likedVideo = { id: 123 };
      jest.spyOn(likedVideoFormService, 'getLikedVideo').mockReturnValue(likedVideo);
      jest.spyOn(likedVideoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ likedVideo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: likedVideo }));
      saveSubject.complete();

      // THEN
      expect(likedVideoFormService.getLikedVideo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(likedVideoService.update).toHaveBeenCalledWith(expect.objectContaining(likedVideo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILikedVideo>>();
      const likedVideo = { id: 123 };
      jest.spyOn(likedVideoFormService, 'getLikedVideo').mockReturnValue({ id: null });
      jest.spyOn(likedVideoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ likedVideo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: likedVideo }));
      saveSubject.complete();

      // THEN
      expect(likedVideoFormService.getLikedVideo).toHaveBeenCalled();
      expect(likedVideoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILikedVideo>>();
      const likedVideo = { id: 123 };
      jest.spyOn(likedVideoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ likedVideo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(likedVideoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
