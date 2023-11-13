import dayjs from 'dayjs/esm';

import { IVideoCategory, NewVideoCategory } from './video-category.model';

export const sampleWithRequiredData: IVideoCategory = {
  id: 20026,
  code: 'phew merrily',
  title: 'suddenly reschedule',
};

export const sampleWithPartialData: IVideoCategory = {
  id: 16007,
  code: 'float upright bah',
  title: 'likewise unaccountably',
  released: dayjs('2023-11-13T13:19'),
};

export const sampleWithFullData: IVideoCategory = {
  id: 13848,
  code: 'greedily',
  title: 'omission till instead',
  created: dayjs('2023-11-13T15:19'),
  released: dayjs('2023-11-13T19:28'),
  archived: dayjs('2023-11-13T01:56'),
};

export const sampleWithNewData: NewVideoCategory = {
  code: 'brr purse',
  title: 'where pfft processor',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
