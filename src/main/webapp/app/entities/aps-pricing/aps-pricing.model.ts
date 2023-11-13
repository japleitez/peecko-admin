import { IAgency } from 'app/entities/agency/agency.model';

export interface IApsPricing {
  id: number;
  customerId?: number | null;
  index?: number | null;
  minQuantity?: number | null;
  unitPrice?: number | null;
  agency?: Pick<IAgency, 'id'> | null;
}

export type NewApsPricing = Omit<IApsPricing, 'id'> & { id: null };
