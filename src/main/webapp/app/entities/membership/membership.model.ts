import { ICompany } from 'app/entities/company/company.model';

export interface IMembership {
  id: number;
  period?: string | null;
  license?: string | null;
  email?: string | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewMembership = Omit<IMembership, 'id'> & { id: null };
