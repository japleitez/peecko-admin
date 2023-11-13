import { Language } from 'app/entities/enumerations/language.model';

export interface ICodeTranslation {
  id: number;
  code?: string | null;
  lang?: keyof typeof Language | null;
  translation?: string | null;
}

export type NewCodeTranslation = Omit<ICodeTranslation, 'id'> & { id: null };
