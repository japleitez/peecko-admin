import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VideoFormService } from './video-form.service';
import { VideoService } from '../service/video.service';
import { IVideo } from '../video.model';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { ICoach } from 'app/entities/coach/coach.model';
import { CoachService } from 'app/entities/coach/service/coach.service';

import { VideoUpdateComponent } from './video-update.component';

describe('Video Management Update Component', () => {
  let comp: VideoUpdateComponent;
  let fixture: ComponentFixture<VideoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let videoFormService: VideoFormService;
  let videoService: VideoService;
  let categoryService: CategoryService;
  let coachService: CoachService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VideoUpdateComponent],
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
      .overrideTemplate(VideoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VideoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    videoFormService = TestBed.inject(VideoFormService);
    videoService = TestBed.inject(VideoService);
    categoryService = TestBed.inject(CategoryService);
    coachService = TestBed.inject(CoachService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Category query and add missing value', () => {
      const video: IVideo = { id: 456 };
      const category: ICategory = { id: 23157 };
      video.category = category;

      const categoryCollection: ICategory[] = [{ id: 19441 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ video });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        categoryCollection,
        ...additionalCategories.map(expect.objectContaining)
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Coach query and add missing value', () => {
      const video: IVideo = { id: 456 };
      const coach: ICoach = { id: 73673 };
      video.coach = coach;

      const coachCollection: ICoach[] = [{ id: 46132 }];
      jest.spyOn(coachService, 'query').mockReturnValue(of(new HttpResponse({ body: coachCollection })));
      const additionalCoaches = [coach];
      const expectedCollection: ICoach[] = [...additionalCoaches, ...coachCollection];
      jest.spyOn(coachService, 'addCoachToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ video });
      comp.ngOnInit();

      expect(coachService.query).toHaveBeenCalled();
      expect(coachService.addCoachToCollectionIfMissing).toHaveBeenCalledWith(
        coachCollection,
        ...additionalCoaches.map(expect.objectContaining)
      );
      expect(comp.coachesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const video: IVideo = { id: 456 };
      const category: ICategory = { id: 23031 };
      video.category = category;
      const coach: ICoach = { id: 97573 };
      video.coach = coach;

      activatedRoute.data = of({ video });
      comp.ngOnInit();

      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.coachesSharedCollection).toContain(coach);
      expect(comp.video).toEqual(video);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideo>>();
      const video = { id: 123 };
      jest.spyOn(videoFormService, 'getVideo').mockReturnValue(video);
      jest.spyOn(videoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ video });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: video }));
      saveSubject.complete();

      // THEN
      expect(videoFormService.getVideo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(videoService.update).toHaveBeenCalledWith(expect.objectContaining(video));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideo>>();
      const video = { id: 123 };
      jest.spyOn(videoFormService, 'getVideo').mockReturnValue({ id: null });
      jest.spyOn(videoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ video: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: video }));
      saveSubject.complete();

      // THEN
      expect(videoFormService.getVideo).toHaveBeenCalled();
      expect(videoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideo>>();
      const video = { id: 123 };
      jest.spyOn(videoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ video });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(videoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategory', () => {
      it('Should forward to categoryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoryService, 'compareCategory');
        comp.compareCategory(entity, entity2);
        expect(categoryService.compareCategory).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCoach', () => {
      it('Should forward to coachService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(coachService, 'compareCoach');
        comp.compareCoach(entity, entity2);
        expect(coachService.compareCoach).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
