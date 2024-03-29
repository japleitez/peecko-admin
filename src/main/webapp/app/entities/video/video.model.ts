import dayjs from 'dayjs/esm';
import { IVideoCategory } from 'app/entities/video-category/video-category.model';
import { ICoach } from 'app/entities/coach/coach.model';
import { Language } from 'app/entities/enumerations/language.model';
import { Player } from 'app/entities/enumerations/player.model';

export interface IVideo {
  id: number;
  code?: string | null;
  title?: string | null;
  duration?: number | null;
  language?: keyof typeof Language | null;
  player?: keyof typeof Player | null;
  thumbnail?: string | null;
  url?: string | null;
  audience?: string | null;
  intensity?: string | null;
  tags?: string | null;
  filename?: string | null;
  description?: string | null;
  created?: dayjs.Dayjs | null;
  released?: dayjs.Dayjs | null;
  archived?: dayjs.Dayjs | null;
  videoCategory?: Pick<IVideoCategory, 'id'> | null;
  coach?: Pick<ICoach, 'id'> | null;
}

export type NewVideo = Omit<IVideo, 'id'> & { id: null };
