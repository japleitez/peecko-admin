import dayjs from 'dayjs/esm';

export interface ICoach {
  id: number;
  name?: string | null;
  email?: string | null;
  website?: string | null;
  instagram?: string | null;
  phoneNumber?: string | null;
  country?: string | null;
  speaks?: string | null;
  resume?: string | null;
  notes?: string | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
}

export type NewCoach = Omit<ICoach, 'id'> & { id: null };
