import { Country } from 'app/entities/enumerations/country.model';
import { ClientState } from 'app/entities/enumerations/client-state.model';

export interface ICompany {
  id: number;
  code?: string | null;
  name?: string | null;
  country?: Country | null;
  state?: ClientState | null;
  license?: string | null;
  vatin?: string | null;
}

export type NewCompany = Omit<ICompany, 'id'> & { id: null };
