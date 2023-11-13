import dayjs from 'dayjs/esm';

export interface IVideoCategory {
  id: number;
  code?: string | null;
  title?: string | null;
  created?: dayjs.Dayjs | null;
  released?: dayjs.Dayjs | null;
  archived?: dayjs.Dayjs | null;
}

export type NewVideoCategory = Omit<IVideoCategory, 'id'> & { id: null };
