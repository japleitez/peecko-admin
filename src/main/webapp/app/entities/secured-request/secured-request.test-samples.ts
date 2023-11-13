import dayjs from 'dayjs/esm';

import { ISecuredRequest, NewSecuredRequest } from './secured-request.model';

export const sampleWithRequiredData: ISecuredRequest = {
  id: 26957,
  requestId: 'outside or whose',
  pinCode: 'though',
  email: 'Arielle.Schroeder30@gmail.com',
  created: dayjs('2023-11-13T09:23'),
};

export const sampleWithPartialData: ISecuredRequest = {
  id: 5625,
  requestId: 'yuck sternly',
  pinCode: 'gigantism marimba',
  email: 'Katarina_Ryan14@gmail.com',
  created: dayjs('2023-11-13T17:29'),
};

export const sampleWithFullData: ISecuredRequest = {
  id: 30424,
  requestId: 'notwithstanding',
  pinCode: 'absolute um dance',
  email: 'Mariam_Green38@yahoo.com',
  created: dayjs('2023-11-12T22:39'),
  expires: dayjs('2023-11-13T04:31'),
};

export const sampleWithNewData: NewSecuredRequest = {
  requestId: 'unnaturally',
  pinCode: 'disdain eyeglasses',
  email: 'Chelsie61@hotmail.com',
  created: dayjs('2023-11-13T08:59'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
