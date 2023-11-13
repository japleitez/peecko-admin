export interface IVideoStat {
  id: number;
  videoId?: number | null;
  categoryId?: number | null;
  coachId?: number | null;
  liked?: number | null;
  viewed?: number | null;
}

export type NewVideoStat = Omit<IVideoStat, 'id'> & { id: null };
