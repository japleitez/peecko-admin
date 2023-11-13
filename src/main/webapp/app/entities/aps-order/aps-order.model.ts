import { IApsPlan } from 'app/entities/aps-plan/aps-plan.model';

export interface IApsOrder {
  id: number;
  contract?: string | null;
  period?: number | null;
  license?: string | null;
  unitPrice?: number | null;
  vatRate?: number | null;
  numberOfUsers?: number | null;
  invoiceNumber?: string | null;
  apsPlan?: Pick<IApsPlan, 'id'> | null;
}

export type NewApsOrder = Omit<IApsOrder, 'id'> & { id: null };
