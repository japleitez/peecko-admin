import dayjs from 'dayjs/esm';
import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { Language } from 'app/entities/enumerations/language.model';

export interface IApsAccount {
  id: number;
  name?: string | null;
  username?: string | null;
  verified?: keyof typeof YesNo | null;
  language?: keyof typeof Language | null;
  email?: string | null;
  emailVerified?: keyof typeof YesNo | null;
  license?: string | null;
  active?: keyof typeof YesNo | null;
  password?: string | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
}

export type NewApsAccount = Omit<IApsAccount, 'id'> & { id: null };
