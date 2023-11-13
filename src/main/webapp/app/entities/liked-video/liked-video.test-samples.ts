import dayjs from 'dayjs/esm';

import { ILikedVideo, NewLikedVideo } from './liked-video.model';

export const sampleWithRequiredData: ILikedVideo = {
  id: 6451,
  videoId: 7648,
  personId: 12659,
  coachId: 2446,
  created: dayjs('2023-11-13T14:53'),
};

export const sampleWithPartialData: ILikedVideo = {
  id: 21271,
  videoId: 30115,
  personId: 2441,
  coachId: 27159,
  created: dayjs('2023-11-13T11:45'),
};

export const sampleWithFullData: ILikedVideo = {
  id: 21700,
  videoId: 18033,
  personId: 16259,
  coachId: 22635,
  created: dayjs('2023-11-13T01:13'),
};

export const sampleWithNewData: NewLikedVideo = {
  videoId: 7507,
  personId: 16188,
  coachId: 1961,
  created: dayjs('2023-11-13T16:04'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
