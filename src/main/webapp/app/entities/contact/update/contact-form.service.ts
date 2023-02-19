import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContact, NewContact } from '../contact.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContact for edit and NewContactFormGroupInput for create.
 */
type ContactFormGroupInput = IContact | PartialWithRequiredKeyOf<NewContact>;

type ContactFormDefaults = Pick<NewContact, 'id'>;

type ContactFormGroupContent = {
  id: FormControl<IContact['id'] | NewContact['id']>;
  type: FormControl<IContact['type']>;
  name: FormControl<IContact['name']>;
  email: FormControl<IContact['email']>;
  phone: FormControl<IContact['phone']>;
  unit: FormControl<IContact['unit']>;
  street: FormControl<IContact['street']>;
  number: FormControl<IContact['number']>;
  zip: FormControl<IContact['zip']>;
  city: FormControl<IContact['city']>;
  country: FormControl<IContact['country']>;
  company: FormControl<IContact['company']>;
};

export type ContactFormGroup = FormGroup<ContactFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContactFormService {
  createContactFormGroup(contact: ContactFormGroupInput = { id: null }): ContactFormGroup {
    const contactRawValue = {
      ...this.getFormDefaults(),
      ...contact,
    };
    return new FormGroup<ContactFormGroupContent>({
      id: new FormControl(
        { value: contactRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(contactRawValue.type, {
        validators: [Validators.required],
      }),
      name: new FormControl(contactRawValue.name),
      email: new FormControl(contactRawValue.email),
      phone: new FormControl(contactRawValue.phone),
      unit: new FormControl(contactRawValue.unit),
      street: new FormControl(contactRawValue.street),
      number: new FormControl(contactRawValue.number),
      zip: new FormControl(contactRawValue.zip),
      city: new FormControl(contactRawValue.city),
      country: new FormControl(contactRawValue.country),
      company: new FormControl(contactRawValue.company),
    });
  }

  getContact(form: ContactFormGroup): IContact | NewContact {
    return form.getRawValue() as IContact | NewContact;
  }

  resetForm(form: ContactFormGroup, contact: ContactFormGroupInput): void {
    const contactRawValue = { ...this.getFormDefaults(), ...contact };
    form.reset(
      {
        ...contactRawValue,
        id: { value: contactRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ContactFormDefaults {
    return {
      id: null,
    };
  }
}
