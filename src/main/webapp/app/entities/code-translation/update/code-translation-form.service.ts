import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICodeTranslation, NewCodeTranslation } from '../code-translation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICodeTranslation for edit and NewCodeTranslationFormGroupInput for create.
 */
type CodeTranslationFormGroupInput = ICodeTranslation | PartialWithRequiredKeyOf<NewCodeTranslation>;

type CodeTranslationFormDefaults = Pick<NewCodeTranslation, 'id'>;

type CodeTranslationFormGroupContent = {
  id: FormControl<ICodeTranslation['id'] | NewCodeTranslation['id']>;
  code: FormControl<ICodeTranslation['code']>;
  lang: FormControl<ICodeTranslation['lang']>;
  translation: FormControl<ICodeTranslation['translation']>;
};

export type CodeTranslationFormGroup = FormGroup<CodeTranslationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CodeTranslationFormService {
  createCodeTranslationFormGroup(codeTranslation: CodeTranslationFormGroupInput = { id: null }): CodeTranslationFormGroup {
    const codeTranslationRawValue = {
      ...this.getFormDefaults(),
      ...codeTranslation,
    };
    return new FormGroup<CodeTranslationFormGroupContent>({
      id: new FormControl(
        { value: codeTranslationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(codeTranslationRawValue.code, {
        validators: [Validators.required],
      }),
      lang: new FormControl(codeTranslationRawValue.lang, {
        validators: [Validators.required],
      }),
      translation: new FormControl(codeTranslationRawValue.translation, {
        validators: [Validators.required],
      }),
    });
  }

  getCodeTranslation(form: CodeTranslationFormGroup): ICodeTranslation | NewCodeTranslation {
    return form.getRawValue() as ICodeTranslation | NewCodeTranslation;
  }

  resetForm(form: CodeTranslationFormGroup, codeTranslation: CodeTranslationFormGroupInput): void {
    const codeTranslationRawValue = { ...this.getFormDefaults(), ...codeTranslation };
    form.reset(
      {
        ...codeTranslationRawValue,
        id: { value: codeTranslationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CodeTranslationFormDefaults {
    return {
      id: null,
    };
  }
}
