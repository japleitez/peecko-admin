import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlan, NewPlan } from '../plan.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlan for edit and NewPlanFormGroupInput for create.
 */
type PlanFormGroupInput = IPlan | PartialWithRequiredKeyOf<NewPlan>;

type PlanFormDefaults = Pick<NewPlan, 'id'>;

type PlanFormGroupContent = {
  id: FormControl<IPlan['id'] | NewPlan['id']>;
  trial: FormControl<IPlan['trial']>;
  active: FormControl<IPlan['active']>;
  price: FormControl<IPlan['price']>;
  start: FormControl<IPlan['start']>;
  end: FormControl<IPlan['end']>;
  company: FormControl<IPlan['company']>;
};

export type PlanFormGroup = FormGroup<PlanFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlanFormService {
  createPlanFormGroup(plan: PlanFormGroupInput = { id: null }): PlanFormGroup {
    const planRawValue = {
      ...this.getFormDefaults(),
      ...plan,
    };
    return new FormGroup<PlanFormGroupContent>({
      id: new FormControl(
        { value: planRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      trial: new FormControl(planRawValue.trial, {
        validators: [Validators.required],
      }),
      active: new FormControl(planRawValue.active, {
        validators: [Validators.required],
      }),
      price: new FormControl(planRawValue.price, {
        validators: [Validators.required],
      }),
      start: new FormControl(planRawValue.start, {
        validators: [Validators.required],
      }),
      end: new FormControl(planRawValue.end),
      company: new FormControl(planRawValue.company),
    });
  }

  getPlan(form: PlanFormGroup): IPlan | NewPlan {
    return form.getRawValue() as IPlan | NewPlan;
  }

  resetForm(form: PlanFormGroup, plan: PlanFormGroupInput): void {
    const planRawValue = { ...this.getFormDefaults(), ...plan };
    form.reset(
      {
        ...planRawValue,
        id: { value: planRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PlanFormDefaults {
    return {
      id: null,
    };
  }
}
