import dayjs from 'dayjs/esm';

export interface ILikedVideo {
  id: number;
  videoId?: number | null;
  personId?: number | null;
  coachId?: number | null;
  created?: dayjs.Dayjs | null;
}

export type NewLikedVideo = Omit<ILikedVideo, 'id'> & { id: null };
