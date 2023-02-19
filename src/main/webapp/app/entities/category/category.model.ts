import { CategoryType } from 'app/entities/enumerations/category-type.model';
import { LiveState } from 'app/entities/enumerations/live-state.model';

export interface ICategory {
  id: number;
  code?: string | null;
  type?: CategoryType | null;
  state?: LiveState | null;
  name?: string | null;
  label?: string | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
