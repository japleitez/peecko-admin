import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMembership, NewMembership } from '../membership.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMembership for edit and NewMembershipFormGroupInput for create.
 */
type MembershipFormGroupInput = IMembership | PartialWithRequiredKeyOf<NewMembership>;

type MembershipFormDefaults = Pick<NewMembership, 'id'>;

type MembershipFormGroupContent = {
  id: FormControl<IMembership['id'] | NewMembership['id']>;
  period: FormControl<IMembership['period']>;
  license: FormControl<IMembership['license']>;
  email: FormControl<IMembership['email']>;
  company: FormControl<IMembership['company']>;
};

export type MembershipFormGroup = FormGroup<MembershipFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MembershipFormService {
  createMembershipFormGroup(membership: MembershipFormGroupInput = { id: null }): MembershipFormGroup {
    const membershipRawValue = {
      ...this.getFormDefaults(),
      ...membership,
    };
    return new FormGroup<MembershipFormGroupContent>({
      id: new FormControl(
        { value: membershipRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      period: new FormControl(membershipRawValue.period, {
        validators: [Validators.required],
      }),
      license: new FormControl(membershipRawValue.license, {
        validators: [Validators.required],
      }),
      email: new FormControl(membershipRawValue.email, {
        validators: [Validators.required],
      }),
      company: new FormControl(membershipRawValue.company),
    });
  }

  getMembership(form: MembershipFormGroup): IMembership | NewMembership {
    return form.getRawValue() as IMembership | NewMembership;
  }

  resetForm(form: MembershipFormGroup, membership: MembershipFormGroupInput): void {
    const membershipRawValue = { ...this.getFormDefaults(), ...membership };
    form.reset(
      {
        ...membershipRawValue,
        id: { value: membershipRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MembershipFormDefaults {
    return {
      id: null,
    };
  }
}
