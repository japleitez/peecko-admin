import { ICompany } from 'app/entities/company/company.model';
import { ContactType } from 'app/entities/enumerations/contact-type.model';

export interface IContact {
  id: number;
  type?: ContactType | null;
  name?: string | null;
  email?: string | null;
  phone?: string | null;
  unit?: string | null;
  street?: string | null;
  number?: string | null;
  zip?: string | null;
  city?: string | null;
  country?: string | null;
  company?: Pick<ICompany, 'id'> | null;
}

export type NewContact = Omit<IContact, 'id'> & { id: null };
