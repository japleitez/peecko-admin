import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISecuredRequest, NewSecuredRequest } from '../secured-request.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISecuredRequest for edit and NewSecuredRequestFormGroupInput for create.
 */
type SecuredRequestFormGroupInput = ISecuredRequest | PartialWithRequiredKeyOf<NewSecuredRequest>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISecuredRequest | NewSecuredRequest> = Omit<T, 'created' | 'expires'> & {
  created?: string | null;
  expires?: string | null;
};

type SecuredRequestFormRawValue = FormValueOf<ISecuredRequest>;

type NewSecuredRequestFormRawValue = FormValueOf<NewSecuredRequest>;

type SecuredRequestFormDefaults = Pick<NewSecuredRequest, 'id' | 'created' | 'expires'>;

type SecuredRequestFormGroupContent = {
  id: FormControl<SecuredRequestFormRawValue['id'] | NewSecuredRequest['id']>;
  requestId: FormControl<SecuredRequestFormRawValue['requestId']>;
  pinCode: FormControl<SecuredRequestFormRawValue['pinCode']>;
  email: FormControl<SecuredRequestFormRawValue['email']>;
  created: FormControl<SecuredRequestFormRawValue['created']>;
  expires: FormControl<SecuredRequestFormRawValue['expires']>;
};

export type SecuredRequestFormGroup = FormGroup<SecuredRequestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SecuredRequestFormService {
  createSecuredRequestFormGroup(securedRequest: SecuredRequestFormGroupInput = { id: null }): SecuredRequestFormGroup {
    const securedRequestRawValue = this.convertSecuredRequestToSecuredRequestRawValue({
      ...this.getFormDefaults(),
      ...securedRequest,
    });
    return new FormGroup<SecuredRequestFormGroupContent>({
      id: new FormControl(
        { value: securedRequestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      requestId: new FormControl(securedRequestRawValue.requestId, {
        validators: [Validators.required],
      }),
      pinCode: new FormControl(securedRequestRawValue.pinCode, {
        validators: [Validators.required],
      }),
      email: new FormControl(securedRequestRawValue.email, {
        validators: [Validators.required],
      }),
      created: new FormControl(securedRequestRawValue.created, {
        validators: [Validators.required],
      }),
      expires: new FormControl(securedRequestRawValue.expires),
    });
  }

  getSecuredRequest(form: SecuredRequestFormGroup): ISecuredRequest | NewSecuredRequest {
    return this.convertSecuredRequestRawValueToSecuredRequest(
      form.getRawValue() as SecuredRequestFormRawValue | NewSecuredRequestFormRawValue,
    );
  }

  resetForm(form: SecuredRequestFormGroup, securedRequest: SecuredRequestFormGroupInput): void {
    const securedRequestRawValue = this.convertSecuredRequestToSecuredRequestRawValue({ ...this.getFormDefaults(), ...securedRequest });
    form.reset(
      {
        ...securedRequestRawValue,
        id: { value: securedRequestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SecuredRequestFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      expires: currentTime,
    };
  }

  private convertSecuredRequestRawValueToSecuredRequest(
    rawSecuredRequest: SecuredRequestFormRawValue | NewSecuredRequestFormRawValue,
  ): ISecuredRequest | NewSecuredRequest {
    return {
      ...rawSecuredRequest,
      created: dayjs(rawSecuredRequest.created, DATE_TIME_FORMAT),
      expires: dayjs(rawSecuredRequest.expires, DATE_TIME_FORMAT),
    };
  }

  private convertSecuredRequestToSecuredRequestRawValue(
    securedRequest: ISecuredRequest | (Partial<NewSecuredRequest> & SecuredRequestFormDefaults),
  ): SecuredRequestFormRawValue | PartialWithRequiredKeyOf<NewSecuredRequestFormRawValue> {
    return {
      ...securedRequest,
      created: securedRequest.created ? securedRequest.created.format(DATE_TIME_FORMAT) : undefined,
      expires: securedRequest.expires ? securedRequest.expires.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
