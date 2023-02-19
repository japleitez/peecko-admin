import { IPerson, NewPerson } from './person.model';

export const sampleWithRequiredData: IPerson = {
  id: 31094,
  name: 'Pound Buckinghamshire',
  surname: 'Wooden Sleek',
  email: 'Garret_Hickle@gmail.com',
};

export const sampleWithPartialData: IPerson = {
  id: 17371,
  name: 'tan',
  surname: 'violet capacitor Sleek',
  email: 'Yolanda.Lockman@hotmail.com',
  password: 'Concrete client-driven',
};

export const sampleWithFullData: IPerson = {
  id: 23596,
  name: 'invoice success',
  surname: 'Florida plum modular',
  email: 'Mortimer_Walsh@yahoo.com',
  password: 'XML',
  license: 'Wyoming',
};

export const sampleWithNewData: NewPerson = {
  name: 'TCP SDD',
  surname: 'distributed CSS',
  email: 'Nickolas9@gmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
