import dayjs from 'dayjs/esm';

import { ICoach, NewCoach } from './coach.model';

export const sampleWithRequiredData: ICoach = {
  id: 23117,
  name: 'forenenst',
};

export const sampleWithPartialData: ICoach = {
  id: 25563,
  name: 'leg likewise chronicle',
  phoneNumber: 'max briefly',
  country: 'Cocos (Keeling) Islands',
  speaks: 'citron',
  notes: 'eventually autoimmunity',
  created: dayjs('2023-11-13T20:18'),
  updated: dayjs('2023-11-13T12:49'),
};

export const sampleWithFullData: ICoach = {
  id: 18016,
  name: 'lightly pioneer',
  email: 'Clement_Jast73@gmail.com',
  website: 'movement shed zowie',
  instagram: 'ew immediately indeed',
  phoneNumber: 'for above offensively',
  country: 'Maldives',
  speaks: 'for designate spook',
  resume: 'intensely',
  notes: 'close but',
  created: dayjs('2023-11-13T13:55'),
  updated: dayjs('2023-11-13T10:32'),
};

export const sampleWithNewData: NewCoach = {
  name: 'itinerary actually oof',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
