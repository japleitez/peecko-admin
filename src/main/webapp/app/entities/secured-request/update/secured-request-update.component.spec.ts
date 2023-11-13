import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SecuredRequestService } from '../service/secured-request.service';
import { ISecuredRequest } from '../secured-request.model';
import { SecuredRequestFormService } from './secured-request-form.service';

import { SecuredRequestUpdateComponent } from './secured-request-update.component';

describe('SecuredRequest Management Update Component', () => {
  let comp: SecuredRequestUpdateComponent;
  let fixture: ComponentFixture<SecuredRequestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let securedRequestFormService: SecuredRequestFormService;
  let securedRequestService: SecuredRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SecuredRequestUpdateComponent],
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
      .overrideTemplate(SecuredRequestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecuredRequestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    securedRequestFormService = TestBed.inject(SecuredRequestFormService);
    securedRequestService = TestBed.inject(SecuredRequestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const securedRequest: ISecuredRequest = { id: 456 };

      activatedRoute.data = of({ securedRequest });
      comp.ngOnInit();

      expect(comp.securedRequest).toEqual(securedRequest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISecuredRequest>>();
      const securedRequest = { id: 123 };
      jest.spyOn(securedRequestFormService, 'getSecuredRequest').mockReturnValue(securedRequest);
      jest.spyOn(securedRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securedRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securedRequest }));
      saveSubject.complete();

      // THEN
      expect(securedRequestFormService.getSecuredRequest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(securedRequestService.update).toHaveBeenCalledWith(expect.objectContaining(securedRequest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISecuredRequest>>();
      const securedRequest = { id: 123 };
      jest.spyOn(securedRequestFormService, 'getSecuredRequest').mockReturnValue({ id: null });
      jest.spyOn(securedRequestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securedRequest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securedRequest }));
      saveSubject.complete();

      // THEN
      expect(securedRequestFormService.getSecuredRequest).toHaveBeenCalled();
      expect(securedRequestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISecuredRequest>>();
      const securedRequest = { id: 123 };
      jest.spyOn(securedRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securedRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(securedRequestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
