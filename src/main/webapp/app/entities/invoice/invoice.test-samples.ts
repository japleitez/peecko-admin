import dayjs from 'dayjs/esm';

import { IInvoice, NewInvoice } from './invoice.model';

export const sampleWithRequiredData: IInvoice = {
  id: 32590,
  number: 'baptize frantically eradicate',
  issued: dayjs('2023-11-13T09:15'),
  dueDate: dayjs('2023-11-13'),
  saleDate: dayjs('2023-11-13'),
  subtotal: 18100.31,
  vat: 8737.52,
  total: 6915.97,
};

export const sampleWithPartialData: IInvoice = {
  id: 2337,
  number: 'cheerfully how',
  issued: dayjs('2023-11-13T12:04'),
  dueDate: dayjs('2023-11-13'),
  saleDate: dayjs('2023-11-13'),
  subtotal: 27714.44,
  vat: 25121.02,
  total: 5237.92,
  paidDate: dayjs('2023-11-13'),
};

export const sampleWithFullData: IInvoice = {
  id: 30448,
  number: 'gadzooks wordy',
  issued: dayjs('2023-11-13T07:20'),
  dueDate: dayjs('2023-11-13'),
  saleDate: dayjs('2023-11-13'),
  subtotal: 8596.17,
  vat: 23848.11,
  total: 3627.95,
  paid: 27435.28,
  paidDate: dayjs('2023-11-13'),
  diff: 29941.02,
  notes: 'how',
};

export const sampleWithNewData: NewInvoice = {
  number: 'mosey indeed',
  issued: dayjs('2023-11-13T11:54'),
  dueDate: dayjs('2023-11-13'),
  saleDate: dayjs('2023-11-13'),
  subtotal: 13263.96,
  vat: 27351.38,
  total: 1362.53,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
