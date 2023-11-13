import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CodeTranslationService } from '../service/code-translation.service';
import { ICodeTranslation } from '../code-translation.model';
import { CodeTranslationFormService } from './code-translation-form.service';

import { CodeTranslationUpdateComponent } from './code-translation-update.component';

describe('CodeTranslation Management Update Component', () => {
  let comp: CodeTranslationUpdateComponent;
  let fixture: ComponentFixture<CodeTranslationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let codeTranslationFormService: CodeTranslationFormService;
  let codeTranslationService: CodeTranslationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CodeTranslationUpdateComponent],
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
      .overrideTemplate(CodeTranslationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CodeTranslationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    codeTranslationFormService = TestBed.inject(CodeTranslationFormService);
    codeTranslationService = TestBed.inject(CodeTranslationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const codeTranslation: ICodeTranslation = { id: 456 };

      activatedRoute.data = of({ codeTranslation });
      comp.ngOnInit();

      expect(comp.codeTranslation).toEqual(codeTranslation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeTranslation>>();
      const codeTranslation = { id: 123 };
      jest.spyOn(codeTranslationFormService, 'getCodeTranslation').mockReturnValue(codeTranslation);
      jest.spyOn(codeTranslationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeTranslation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: codeTranslation }));
      saveSubject.complete();

      // THEN
      expect(codeTranslationFormService.getCodeTranslation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(codeTranslationService.update).toHaveBeenCalledWith(expect.objectContaining(codeTranslation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeTranslation>>();
      const codeTranslation = { id: 123 };
      jest.spyOn(codeTranslationFormService, 'getCodeTranslation').mockReturnValue({ id: null });
      jest.spyOn(codeTranslationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeTranslation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: codeTranslation }));
      saveSubject.complete();

      // THEN
      expect(codeTranslationFormService.getCodeTranslation).toHaveBeenCalled();
      expect(codeTranslationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeTranslation>>();
      const codeTranslation = { id: 123 };
      jest.spyOn(codeTranslationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeTranslation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(codeTranslationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
