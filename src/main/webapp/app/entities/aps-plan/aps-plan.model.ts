import dayjs from 'dayjs/esm';
import { ICustomer } from 'app/entities/customer/customer.model';
import { PricingType } from 'app/entities/enumerations/pricing-type.model';
import { PlanState } from 'app/entities/enumerations/plan-state.model';

export interface IApsPlan {
  id: number;
  contract?: string | null;
  pricing?: keyof typeof PricingType | null;
  state?: keyof typeof PlanState | null;
  license?: string | null;
  starts?: dayjs.Dayjs | null;
  ends?: dayjs.Dayjs | null;
  trialStarts?: dayjs.Dayjs | null;
  trialEnds?: dayjs.Dayjs | null;
  unitPrice?: number | null;
  notes?: string | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  customer?: Pick<ICustomer, 'id'> | null;
}

export type NewApsPlan = Omit<IApsPlan, 'id'> & { id: null };
