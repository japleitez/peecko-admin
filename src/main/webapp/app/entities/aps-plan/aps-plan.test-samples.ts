import dayjs from 'dayjs/esm';

import { IApsPlan, NewApsPlan } from './aps-plan.model';

export const sampleWithRequiredData: IApsPlan = {
  id: 4303,
  contract: 'override huzzah rigidly',
  pricing: 'FIXED',
  state: 'ACTIVE',
  unitPrice: 30999.2,
};

export const sampleWithPartialData: IApsPlan = {
  id: 8779,
  contract: 'regarding ringed whoever',
  pricing: 'BRACKET',
  state: 'CLOSED',
  starts: dayjs('2023-11-13'),
  ends: dayjs('2023-11-13'),
  trialStarts: dayjs('2023-11-13'),
  trialEnds: dayjs('2023-11-13'),
  unitPrice: 11558.56,
  notes: 'earnest untie failing',
  created: dayjs('2023-11-13T03:43'),
  updated: dayjs('2023-11-13T20:55'),
};

export const sampleWithFullData: IApsPlan = {
  id: 11497,
  contract: 'crowded',
  pricing: 'BRACKET',
  state: 'TRIAL',
  license: 'lubricate beginner pfft',
  starts: dayjs('2023-11-13'),
  ends: dayjs('2023-11-13'),
  trialStarts: dayjs('2023-11-13'),
  trialEnds: dayjs('2023-11-13'),
  unitPrice: 20745.23,
  notes: 'be except frenetically',
  created: dayjs('2023-11-12T23:54'),
  updated: dayjs('2023-11-13T12:47'),
};

export const sampleWithNewData: NewApsPlan = {
  contract: 'scheme count strive',
  pricing: 'BRACKET',
  state: 'CLOSED',
  unitPrice: 5551.51,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
