import { IAgency } from 'app/entities/agency/agency.model';

export interface IStaff {
  id: number;
  staffId?: number | null;
  agency?: Pick<IAgency, 'id'> | null;
}

export type NewStaff = Omit<IStaff, 'id'> & { id: null };
