import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IApsAccount } from 'app/entities/aps-account/aps-account.model';
import { ApsAccountService } from 'app/entities/aps-account/service/aps-account.service';
import { ApsDeviceService } from '../service/aps-device.service';
import { IApsDevice } from '../aps-device.model';
import { ApsDeviceFormService } from './aps-device-form.service';

import { ApsDeviceUpdateComponent } from './aps-device-update.component';

describe('ApsDevice Management Update Component', () => {
  let comp: ApsDeviceUpdateComponent;
  let fixture: ComponentFixture<ApsDeviceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let apsDeviceFormService: ApsDeviceFormService;
  let apsDeviceService: ApsDeviceService;
  let apsAccountService: ApsAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ApsDeviceUpdateComponent],
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
      .overrideTemplate(ApsDeviceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApsDeviceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    apsDeviceFormService = TestBed.inject(ApsDeviceFormService);
    apsDeviceService = TestBed.inject(ApsDeviceService);
    apsAccountService = TestBed.inject(ApsAccountService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ApsAccount query and add missing value', () => {
      const apsDevice: IApsDevice = { id: 456 };
      const apsAccount: IApsAccount = { id: 21608 };
      apsDevice.apsAccount = apsAccount;

      const apsAccountCollection: IApsAccount[] = [{ id: 27133 }];
      jest.spyOn(apsAccountService, 'query').mockReturnValue(of(new HttpResponse({ body: apsAccountCollection })));
      const additionalApsAccounts = [apsAccount];
      const expectedCollection: IApsAccount[] = [...additionalApsAccounts, ...apsAccountCollection];
      jest.spyOn(apsAccountService, 'addApsAccountToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ apsDevice });
      comp.ngOnInit();

      expect(apsAccountService.query).toHaveBeenCalled();
      expect(apsAccountService.addApsAccountToCollectionIfMissing).toHaveBeenCalledWith(
        apsAccountCollection,
        ...additionalApsAccounts.map(expect.objectContaining),
      );
      expect(comp.apsAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const apsDevice: IApsDevice = { id: 456 };
      const apsAccount: IApsAccount = { id: 27418 };
      apsDevice.apsAccount = apsAccount;

      activatedRoute.data = of({ apsDevice });
      comp.ngOnInit();

      expect(comp.apsAccountsSharedCollection).toContain(apsAccount);
      expect(comp.apsDevice).toEqual(apsDevice);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApsDevice>>();
      const apsDevice = { id: 123 };
      jest.spyOn(apsDeviceFormService, 'getApsDevice').mockReturnValue(apsDevice);
      jest.spyOn(apsDeviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apsDevice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apsDevice }));
      saveSubject.complete();

      // THEN
      expect(apsDeviceFormService.getApsDevice).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(apsDeviceService.update).toHaveBeenCalledWith(expect.objectContaining(apsDevice));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApsDevice>>();
      const apsDevice = { id: 123 };
      jest.spyOn(apsDeviceFormService, 'getApsDevice').mockReturnValue({ id: null });
      jest.spyOn(apsDeviceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apsDevice: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apsDevice }));
      saveSubject.complete();

      // THEN
      expect(apsDeviceFormService.getApsDevice).toHaveBeenCalled();
      expect(apsDeviceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApsDevice>>();
      const apsDevice = { id: 123 };
      jest.spyOn(apsDeviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apsDevice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(apsDeviceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareApsAccount', () => {
      it('Should forward to apsAccountService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(apsAccountService, 'compareApsAccount');
        comp.compareApsAccount(entity, entity2);
        expect(apsAccountService.compareApsAccount).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
