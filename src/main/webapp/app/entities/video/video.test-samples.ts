import dayjs from 'dayjs/esm';

import { IVideo, NewVideo } from './video.model';

export const sampleWithRequiredData: IVideo = {
  id: 3524,
  code: 'mmm teethe dude',
  title: 'quarrelsomely',
  language: 'ES',
  player: 'PEECKO',
};

export const sampleWithPartialData: IVideo = {
  id: 765,
  code: 'rigidly',
  title: 'meanwhile unique warmly',
  language: 'FR',
  player: 'PEECKO',
  url: 'https://tender-bench.biz/',
  audience: 'incidentally',
  intensity: 'offer who consignment',
  description: 'caress',
  created: dayjs('2023-11-13T07:07'),
  archived: dayjs('2023-11-13T08:46'),
};

export const sampleWithFullData: IVideo = {
  id: 29902,
  code: 'across consequently gah',
  title: 'inside tensely',
  duration: 11098,
  language: 'EN',
  player: 'PEECKO',
  thumbnail: 'lecture aha',
  url: 'https://brisk-eaves.info',
  audience: 'er',
  intensity: 'bolt',
  tags: 'very chocolate aha',
  filename: 'beautifully',
  description: 'blight',
  created: dayjs('2023-11-13T07:19'),
  released: dayjs('2023-11-13T12:04'),
  archived: dayjs('2023-11-13T02:24'),
};

export const sampleWithNewData: NewVideo = {
  code: 'sundial',
  title: 'over',
  language: 'ES',
  player: 'PEECKO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
