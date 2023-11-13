import { IVideoStat, NewVideoStat } from './video-stat.model';

export const sampleWithRequiredData: IVideoStat = {
  id: 31558,
  videoId: 96,
  categoryId: 6547,
  coachId: 15869,
  liked: 11960,
  viewed: 24569,
};

export const sampleWithPartialData: IVideoStat = {
  id: 11020,
  videoId: 10510,
  categoryId: 25144,
  coachId: 23825,
  liked: 4295,
  viewed: 17122,
};

export const sampleWithFullData: IVideoStat = {
  id: 13074,
  videoId: 32690,
  categoryId: 20059,
  coachId: 3257,
  liked: 17106,
  viewed: 13703,
};

export const sampleWithNewData: NewVideoStat = {
  videoId: 21081,
  categoryId: 10150,
  coachId: 10814,
  liked: 20632,
  viewed: 24620,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
