import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../secured-request.test-samples';

import { SecuredRequestFormService } from './secured-request-form.service';

describe('SecuredRequest Form Service', () => {
  let service: SecuredRequestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SecuredRequestFormService);
  });

  describe('Service methods', () => {
    describe('createSecuredRequestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSecuredRequestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            requestId: expect.any(Object),
            pinCode: expect.any(Object),
            email: expect.any(Object),
            created: expect.any(Object),
            expires: expect.any(Object),
          }),
        );
      });

      it('passing ISecuredRequest should create a new form with FormGroup', () => {
        const formGroup = service.createSecuredRequestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            requestId: expect.any(Object),
            pinCode: expect.any(Object),
            email: expect.any(Object),
            created: expect.any(Object),
            expires: expect.any(Object),
          }),
        );
      });
    });

    describe('getSecuredRequest', () => {
      it('should return NewSecuredRequest for default SecuredRequest initial value', () => {
        const formGroup = service.createSecuredRequestFormGroup(sampleWithNewData);

        const securedRequest = service.getSecuredRequest(formGroup) as any;

        expect(securedRequest).toMatchObject(sampleWithNewData);
      });

      it('should return NewSecuredRequest for empty SecuredRequest initial value', () => {
        const formGroup = service.createSecuredRequestFormGroup();

        const securedRequest = service.getSecuredRequest(formGroup) as any;

        expect(securedRequest).toMatchObject({});
      });

      it('should return ISecuredRequest', () => {
        const formGroup = service.createSecuredRequestFormGroup(sampleWithRequiredData);

        const securedRequest = service.getSecuredRequest(formGroup) as any;

        expect(securedRequest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISecuredRequest should not enable id FormControl', () => {
        const formGroup = service.createSecuredRequestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSecuredRequest should disable id FormControl', () => {
        const formGroup = service.createSecuredRequestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
