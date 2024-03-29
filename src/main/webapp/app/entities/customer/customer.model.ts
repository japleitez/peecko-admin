import dayjs from 'dayjs/esm';
import { IAgency } from 'app/entities/agency/agency.model';
import { CustomerState } from 'app/entities/enumerations/customer-state.model';

export interface ICustomer {
  id: number;
  code?: string | null;
  name?: string | null;
  country?: string | null;
  membership?: string | null;
  state?: keyof typeof CustomerState | null;
  closeReason?: string | null;
  emailDomains?: string | null;
  vatId?: string | null;
  bank?: string | null;
  iban?: string | null;
  logo?: string | null;
  notes?: string | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  trialed?: dayjs.Dayjs | null;
  declined?: dayjs.Dayjs | null;
  activated?: dayjs.Dayjs | null;
  closed?: dayjs.Dayjs | null;
  agency?: Pick<IAgency, 'id'> | null;
}

export type NewCustomer = Omit<ICustomer, 'id'> & { id: null };
