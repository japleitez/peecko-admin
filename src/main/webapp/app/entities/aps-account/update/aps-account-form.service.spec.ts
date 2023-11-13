import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../aps-account.test-samples';

import { ApsAccountFormService } from './aps-account-form.service';

describe('ApsAccount Form Service', () => {
  let service: ApsAccountFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApsAccountFormService);
  });

  describe('Service methods', () => {
    describe('createApsAccountFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createApsAccountFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            username: expect.any(Object),
            verified: expect.any(Object),
            language: expect.any(Object),
            email: expect.any(Object),
            emailVerified: expect.any(Object),
            license: expect.any(Object),
            active: expect.any(Object),
            password: expect.any(Object),
            created: expect.any(Object),
            updated: expect.any(Object),
          }),
        );
      });

      it('passing IApsAccount should create a new form with FormGroup', () => {
        const formGroup = service.createApsAccountFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            username: expect.any(Object),
            verified: expect.any(Object),
            language: expect.any(Object),
            email: expect.any(Object),
            emailVerified: expect.any(Object),
            license: expect.any(Object),
            active: expect.any(Object),
            password: expect.any(Object),
            created: expect.any(Object),
            updated: expect.any(Object),
          }),
        );
      });
    });

    describe('getApsAccount', () => {
      it('should return NewApsAccount for default ApsAccount initial value', () => {
        const formGroup = service.createApsAccountFormGroup(sampleWithNewData);

        const apsAccount = service.getApsAccount(formGroup) as any;

        expect(apsAccount).toMatchObject(sampleWithNewData);
      });

      it('should return NewApsAccount for empty ApsAccount initial value', () => {
        const formGroup = service.createApsAccountFormGroup();

        const apsAccount = service.getApsAccount(formGroup) as any;

        expect(apsAccount).toMatchObject({});
      });

      it('should return IApsAccount', () => {
        const formGroup = service.createApsAccountFormGroup(sampleWithRequiredData);

        const apsAccount = service.getApsAccount(formGroup) as any;

        expect(apsAccount).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IApsAccount should not enable id FormControl', () => {
        const formGroup = service.createApsAccountFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewApsAccount should disable id FormControl', () => {
        const formGroup = service.createApsAccountFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
