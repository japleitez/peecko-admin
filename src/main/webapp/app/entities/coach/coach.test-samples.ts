import { ICoach, NewCoach } from './coach.model';

export const sampleWithRequiredData: ICoach = {
  id: 97953,
  name: 'Metal',
  surname: 'Books',
  email: 'Eldred.Reynolds63@yahoo.com',
};

export const sampleWithPartialData: ICoach = {
  id: 82427,
  name: 'Cheese',
  surname: 'Fantastic',
  email: 'Hudson.Hoppe64@hotmail.com',
  tags: 'lavender Unbranded Auto',
};

export const sampleWithFullData: ICoach = {
  id: 44316,
  name: 'monetize',
  surname: 'Salad deposit archive',
  email: 'Lillian.Kassulke@hotmail.com',
  phone: '1-875-335-9075 x22254',
  resume: 'Product',
  langs: 'Bedfordshire Planner Borders',
  tags: 'e-business blue',
};

export const sampleWithNewData: NewCoach = {
  name: 'Refined Ergonomic',
  surname: 'Seamless Principal hacking',
  email: 'Brent7@gmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
