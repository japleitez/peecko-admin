export interface ICoach {
  id: number;
  name?: string | null;
  surname?: string | null;
  email?: string | null;
  phone?: string | null;
  resume?: string | null;
  langs?: string | null;
  tags?: string | null;
}

export type NewCoach = Omit<ICoach, 'id'> & { id: null };
