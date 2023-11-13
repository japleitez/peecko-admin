import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../code-translation.test-samples';

import { CodeTranslationFormService } from './code-translation-form.service';

describe('CodeTranslation Form Service', () => {
  let service: CodeTranslationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CodeTranslationFormService);
  });

  describe('Service methods', () => {
    describe('createCodeTranslationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCodeTranslationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            lang: expect.any(Object),
            translation: expect.any(Object),
          }),
        );
      });

      it('passing ICodeTranslation should create a new form with FormGroup', () => {
        const formGroup = service.createCodeTranslationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            lang: expect.any(Object),
            translation: expect.any(Object),
          }),
        );
      });
    });

    describe('getCodeTranslation', () => {
      it('should return NewCodeTranslation for default CodeTranslation initial value', () => {
        const formGroup = service.createCodeTranslationFormGroup(sampleWithNewData);

        const codeTranslation = service.getCodeTranslation(formGroup) as any;

        expect(codeTranslation).toMatchObject(sampleWithNewData);
      });

      it('should return NewCodeTranslation for empty CodeTranslation initial value', () => {
        const formGroup = service.createCodeTranslationFormGroup();

        const codeTranslation = service.getCodeTranslation(formGroup) as any;

        expect(codeTranslation).toMatchObject({});
      });

      it('should return ICodeTranslation', () => {
        const formGroup = service.createCodeTranslationFormGroup(sampleWithRequiredData);

        const codeTranslation = service.getCodeTranslation(formGroup) as any;

        expect(codeTranslation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICodeTranslation should not enable id FormControl', () => {
        const formGroup = service.createCodeTranslationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCodeTranslation should disable id FormControl', () => {
        const formGroup = service.createCodeTranslationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
