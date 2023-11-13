import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ApsAccountService } from '../service/aps-account.service';
import { IApsAccount } from '../aps-account.model';
import { ApsAccountFormService } from './aps-account-form.service';

import { ApsAccountUpdateComponent } from './aps-account-update.component';

describe('ApsAccount Management Update Component', () => {
  let comp: ApsAccountUpdateComponent;
  let fixture: ComponentFixture<ApsAccountUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let apsAccountFormService: ApsAccountFormService;
  let apsAccountService: ApsAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ApsAccountUpdateComponent],
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
      .overrideTemplate(ApsAccountUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApsAccountUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    apsAccountFormService = TestBed.inject(ApsAccountFormService);
    apsAccountService = TestBed.inject(ApsAccountService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const apsAccount: IApsAccount = { id: 456 };

      activatedRoute.data = of({ apsAccount });
      comp.ngOnInit();

      expect(comp.apsAccount).toEqual(apsAccount);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApsAccount>>();
      const apsAccount = { id: 123 };
      jest.spyOn(apsAccountFormService, 'getApsAccount').mockReturnValue(apsAccount);
      jest.spyOn(apsAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apsAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apsAccount }));
      saveSubject.complete();

      // THEN
      expect(apsAccountFormService.getApsAccount).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(apsAccountService.update).toHaveBeenCalledWith(expect.objectContaining(apsAccount));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApsAccount>>();
      const apsAccount = { id: 123 };
      jest.spyOn(apsAccountFormService, 'getApsAccount').mockReturnValue({ id: null });
      jest.spyOn(apsAccountService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apsAccount: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apsAccount }));
      saveSubject.complete();

      // THEN
      expect(apsAccountFormService.getApsAccount).toHaveBeenCalled();
      expect(apsAccountService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApsAccount>>();
      const apsAccount = { id: 123 };
      jest.spyOn(apsAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apsAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(apsAccountService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
