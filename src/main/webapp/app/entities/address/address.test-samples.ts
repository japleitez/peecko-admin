import { ContactType } from 'app/entities/enumerations/contact-type.model';

import { IContact, NewContact } from '../contact/contact.model';

export const sampleWithRequiredData: IContact = {
  id: 36482,
  type: ContactType['MANAGER'],
};

export const sampleWithPartialData: IContact = {
  id: 96956,
  type: ContactType['OPERATION'],
  name: 'Outdoors forecast Lari',
  number: 'navigate Supervisor Granite',
  zip: 'Manager Cambridgeshire',
  city: 'Myaside',
  country: 'Spain',
};

export const sampleWithFullData: IContact = {
  id: 82211,
  type: ContactType['MANAGER'],
  name: 'pink generating THX',
  email: 'Marques_Cole@gmail.com',
  phone: '(669) 538-0759',
  unit: 'Upgradable parsing',
  street: 'Barrows Forge',
  number: 'client-driven Account',
  zip: 'systems applications',
  city: 'West Zanderville',
  country: 'Brazil',
};

export const sampleWithNewData: NewContact = {
  type: ContactType['BILLING'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
