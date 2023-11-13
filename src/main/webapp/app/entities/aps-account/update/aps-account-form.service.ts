import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IApsAccount, NewApsAccount } from '../aps-account.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IApsAccount for edit and NewApsAccountFormGroupInput for create.
 */
type ApsAccountFormGroupInput = IApsAccount | PartialWithRequiredKeyOf<NewApsAccount>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IApsAccount | NewApsAccount> = Omit<T, 'created' | 'updated'> & {
  created?: string | null;
  updated?: string | null;
};

type ApsAccountFormRawValue = FormValueOf<IApsAccount>;

type NewApsAccountFormRawValue = FormValueOf<NewApsAccount>;

type ApsAccountFormDefaults = Pick<NewApsAccount, 'id' | 'created' | 'updated'>;

type ApsAccountFormGroupContent = {
  id: FormControl<ApsAccountFormRawValue['id'] | NewApsAccount['id']>;
  name: FormControl<ApsAccountFormRawValue['name']>;
  username: FormControl<ApsAccountFormRawValue['username']>;
  verified: FormControl<ApsAccountFormRawValue['verified']>;
  language: FormControl<ApsAccountFormRawValue['language']>;
  email: FormControl<ApsAccountFormRawValue['email']>;
  emailVerified: FormControl<ApsAccountFormRawValue['emailVerified']>;
  license: FormControl<ApsAccountFormRawValue['license']>;
  active: FormControl<ApsAccountFormRawValue['active']>;
  password: FormControl<ApsAccountFormRawValue['password']>;
  created: FormControl<ApsAccountFormRawValue['created']>;
  updated: FormControl<ApsAccountFormRawValue['updated']>;
};

export type ApsAccountFormGroup = FormGroup<ApsAccountFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ApsAccountFormService {
  createApsAccountFormGroup(apsAccount: ApsAccountFormGroupInput = { id: null }): ApsAccountFormGroup {
    const apsAccountRawValue = this.convertApsAccountToApsAccountRawValue({
      ...this.getFormDefaults(),
      ...apsAccount,
    });
    return new FormGroup<ApsAccountFormGroupContent>({
      id: new FormControl(
        { value: apsAccountRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(apsAccountRawValue.name, {
        validators: [Validators.required],
      }),
      username: new FormControl(apsAccountRawValue.username, {
        validators: [Validators.required],
      }),
      verified: new FormControl(apsAccountRawValue.verified, {
        validators: [Validators.required],
      }),
      language: new FormControl(apsAccountRawValue.language, {
        validators: [Validators.required],
      }),
      email: new FormControl(apsAccountRawValue.email),
      emailVerified: new FormControl(apsAccountRawValue.emailVerified, {
        validators: [Validators.required],
      }),
      license: new FormControl(apsAccountRawValue.license),
      active: new FormControl(apsAccountRawValue.active, {
        validators: [Validators.required],
      }),
      password: new FormControl(apsAccountRawValue.password),
      created: new FormControl(apsAccountRawValue.created),
      updated: new FormControl(apsAccountRawValue.updated),
    });
  }

  getApsAccount(form: ApsAccountFormGroup): IApsAccount | NewApsAccount {
    return this.convertApsAccountRawValueToApsAccount(form.getRawValue() as ApsAccountFormRawValue | NewApsAccountFormRawValue);
  }

  resetForm(form: ApsAccountFormGroup, apsAccount: ApsAccountFormGroupInput): void {
    const apsAccountRawValue = this.convertApsAccountToApsAccountRawValue({ ...this.getFormDefaults(), ...apsAccount });
    form.reset(
      {
        ...apsAccountRawValue,
        id: { value: apsAccountRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ApsAccountFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      updated: currentTime,
    };
  }

  private convertApsAccountRawValueToApsAccount(
    rawApsAccount: ApsAccountFormRawValue | NewApsAccountFormRawValue,
  ): IApsAccount | NewApsAccount {
    return {
      ...rawApsAccount,
      created: dayjs(rawApsAccount.created, DATE_TIME_FORMAT),
      updated: dayjs(rawApsAccount.updated, DATE_TIME_FORMAT),
    };
  }

  private convertApsAccountToApsAccountRawValue(
    apsAccount: IApsAccount | (Partial<NewApsAccount> & ApsAccountFormDefaults),
  ): ApsAccountFormRawValue | PartialWithRequiredKeyOf<NewApsAccountFormRawValue> {
    return {
      ...apsAccount,
      created: apsAccount.created ? apsAccount.created.format(DATE_TIME_FORMAT) : undefined,
      updated: apsAccount.updated ? apsAccount.updated.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
