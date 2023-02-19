import { ICategory } from 'app/entities/category/category.model';
import { ICoach } from 'app/entities/coach/coach.model';
import { LiveState } from 'app/entities/enumerations/live-state.model';
import { Lang } from 'app/entities/enumerations/lang.model';

export interface IVideo {
  id: number;
  code?: string | null;
  state?: LiveState | null;
  name?: string | null;
  tags?: string | null;
  lang?: Lang | null;
  url?: string | null;
  category?: Pick<ICategory, 'id'> | null;
  coach?: Pick<ICoach, 'id'> | null;
}

export type NewVideo = Omit<IVideo, 'id'> & { id: null };
