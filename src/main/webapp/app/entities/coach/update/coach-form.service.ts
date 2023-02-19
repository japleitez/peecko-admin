import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICoach, NewCoach } from '../coach.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICoach for edit and NewCoachFormGroupInput for create.
 */
type CoachFormGroupInput = ICoach | PartialWithRequiredKeyOf<NewCoach>;

type CoachFormDefaults = Pick<NewCoach, 'id'>;

type CoachFormGroupContent = {
  id: FormControl<ICoach['id'] | NewCoach['id']>;
  name: FormControl<ICoach['name']>;
  surname: FormControl<ICoach['surname']>;
  email: FormControl<ICoach['email']>;
  phone: FormControl<ICoach['phone']>;
  resume: FormControl<ICoach['resume']>;
  langs: FormControl<ICoach['langs']>;
  tags: FormControl<ICoach['tags']>;
};

export type CoachFormGroup = FormGroup<CoachFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CoachFormService {
  createCoachFormGroup(coach: CoachFormGroupInput = { id: null }): CoachFormGroup {
    const coachRawValue = {
      ...this.getFormDefaults(),
      ...coach,
    };
    return new FormGroup<CoachFormGroupContent>({
      id: new FormControl(
        { value: coachRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(coachRawValue.name, {
        validators: [Validators.required],
      }),
      surname: new FormControl(coachRawValue.surname, {
        validators: [Validators.required],
      }),
      email: new FormControl(coachRawValue.email, {
        validators: [Validators.required],
      }),
      phone: new FormControl(coachRawValue.phone),
      resume: new FormControl(coachRawValue.resume),
      langs: new FormControl(coachRawValue.langs),
      tags: new FormControl(coachRawValue.tags),
    });
  }

  getCoach(form: CoachFormGroup): ICoach | NewCoach {
    return form.getRawValue() as ICoach | NewCoach;
  }

  resetForm(form: CoachFormGroup, coach: CoachFormGroupInput): void {
    const coachRawValue = { ...this.getFormDefaults(), ...coach };
    form.reset(
      {
        ...coachRawValue,
        id: { value: coachRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CoachFormDefaults {
    return {
      id: null,
    };
  }
}
