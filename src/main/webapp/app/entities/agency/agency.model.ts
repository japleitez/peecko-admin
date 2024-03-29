import dayjs from 'dayjs/esm';
import { Language } from 'app/entities/enumerations/language.model';

export interface IAgency {
  id: number;
  code?: string | null;
  name?: string | null;
  line1?: string | null;
  line2?: string | null;
  zip?: string | null;
  city?: string | null;
  country?: string | null;
  language?: keyof typeof Language | null;
  email?: string | null;
  phone?: string | null;
  billingEmail?: string | null;
  billingPhone?: string | null;
  bank?: string | null;
  iban?: string | null;
  rcs?: string | null;
  vatId?: string | null;
  vatRate?: number | null;
  notes?: string | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
}

export type NewAgency = Omit<IAgency, 'id'> & { id: null };
