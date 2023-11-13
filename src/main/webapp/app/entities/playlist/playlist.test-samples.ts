import dayjs from 'dayjs/esm';

import { IPlaylist, NewPlaylist } from './playlist.model';

export const sampleWithRequiredData: IPlaylist = {
  id: 10445,
  name: 'unique skive',
  counter: 27764,
  created: dayjs('2023-11-12T21:39'),
  updated: dayjs('2023-11-13T07:09'),
};

export const sampleWithPartialData: IPlaylist = {
  id: 18664,
  name: 'seclude weekender definitive',
  counter: 8,
  created: dayjs('2023-11-13T10:50'),
  updated: dayjs('2023-11-13T04:24'),
};

export const sampleWithFullData: IPlaylist = {
  id: 423,
  name: 'monthly',
  counter: 28274,
  created: dayjs('2023-11-13T20:16'),
  updated: dayjs('2023-11-13T11:01'),
};

export const sampleWithNewData: NewPlaylist = {
  name: 'enfeeble toothpaste quaver',
  counter: 11152,
  created: dayjs('2023-11-13T19:36'),
  updated: dayjs('2023-11-13T10:50'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
