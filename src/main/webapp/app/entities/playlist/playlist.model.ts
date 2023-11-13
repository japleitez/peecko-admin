import dayjs from 'dayjs/esm';
import { IApsAccount } from 'app/entities/aps-account/aps-account.model';

export interface IPlaylist {
  id: number;
  name?: string | null;
  counter?: number | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  apsAccount?: Pick<IApsAccount, 'id'> | null;
}

export type NewPlaylist = Omit<IPlaylist, 'id'> & { id: null };
