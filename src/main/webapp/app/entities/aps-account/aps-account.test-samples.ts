import dayjs from 'dayjs/esm';

import { IApsAccount, NewApsAccount } from './aps-account.model';

export const sampleWithRequiredData: IApsAccount = {
  id: 26938,
  name: 'on',
  username: 'behind',
  verified: 'Y',
  language: 'DE',
  emailVerified: 'N',
  active: 'N',
};

export const sampleWithPartialData: IApsAccount = {
  id: 23659,
  name: 'through',
  username: 'same',
  verified: 'N',
  language: 'EN',
  emailVerified: 'N',
  license: 'guffaw gadzooks',
  active: 'Y',
  created: dayjs('2023-11-13T13:22'),
  updated: dayjs('2023-11-13T06:27'),
};

export const sampleWithFullData: IApsAccount = {
  id: 28614,
  name: 'consequently',
  username: 'zowie',
  verified: 'Y',
  language: 'FR',
  email: 'Marietta_Bradtke42@yahoo.com',
  emailVerified: 'N',
  license: 'scarcely accomplished',
  active: 'N',
  password: 'of physically mmm',
  created: dayjs('2023-11-13T05:25'),
  updated: dayjs('2023-11-13T14:46'),
};

export const sampleWithNewData: NewApsAccount = {
  name: 'provided hm',
  username: 'an boohoo',
  verified: 'Y',
  language: 'FR',
  emailVerified: 'Y',
  active: 'N',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
