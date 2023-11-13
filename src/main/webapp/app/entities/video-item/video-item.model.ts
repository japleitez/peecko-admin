import { IPlaylist } from 'app/entities/playlist/playlist.model';

export interface IVideoItem {
  id: number;
  previous?: string | null;
  code?: string | null;
  next?: string | null;
  playlist?: Pick<IPlaylist, 'id'> | null;
}

export type NewVideoItem = Omit<IVideoItem, 'id'> & { id: null };
