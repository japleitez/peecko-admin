import dayjs from 'dayjs/esm';

import { YesNo } from 'app/entities/enumerations/yes-no.model';

import { IPlan, NewPlan } from './plan.model';

export const sampleWithRequiredData: IPlan = {
  id: 5214,
  trial: YesNo['Y'],
  active: YesNo['N'],
  price: 73825,
  start: dayjs('2023-02-19'),
};

export const sampleWithPartialData: IPlan = {
  id: 90008,
  trial: YesNo['Y'],
  active: YesNo['N'],
  price: 34665,
  start: dayjs('2023-02-19'),
  end: dayjs('2023-02-19'),
};

export const sampleWithFullData: IPlan = {
  id: 4083,
  trial: YesNo['N'],
  active: YesNo['N'],
  price: 74053,
  start: dayjs('2023-02-18'),
  end: dayjs('2023-02-19'),
};

export const sampleWithNewData: NewPlan = {
  trial: YesNo['N'],
  active: YesNo['Y'],
  price: 96343,
  start: dayjs('2023-02-19'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
