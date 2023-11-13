import dayjs from 'dayjs/esm';

import { INotification, NewNotification } from './notification.model';

export const sampleWithRequiredData: INotification = {
  id: 5562,
  companyId: 5860,
  title: 'fibrosis task within',
  message: 'sycamore baggy against',
};

export const sampleWithPartialData: INotification = {
  id: 15197,
  companyId: 7281,
  title: 'who lest',
  message: 'strip furthermore',
  imageUrl: 'amidst invader',
  videoUrl: 'beloved',
  starts: dayjs('2023-11-13'),
};

export const sampleWithFullData: INotification = {
  id: 27294,
  companyId: 25533,
  title: 'fondly barring beef',
  message: 'brr uproot accidentally',
  imageUrl: 'for ack',
  videoUrl: 'yet',
  starts: dayjs('2023-11-13'),
  expires: dayjs('2023-11-13'),
};

export const sampleWithNewData: NewNotification = {
  companyId: 728,
  title: 'wherever subcomponent',
  message: 'self-assured mid',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
