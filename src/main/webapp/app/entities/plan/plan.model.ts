import dayjs from 'dayjs/esm';
import { ICompany } from 'app/entities/company/company.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IPlan {
  id: number;
  trial?: YesNo | null;
  active?: YesNo | null;
  price?: number | null;
  start?: dayjs.Dayjs | null;
  end?: dayjs.Dayjs | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewPlan = Omit<IPlan, 'id'> & { id: null };
