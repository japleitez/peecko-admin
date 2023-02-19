export interface IPerson {
  id: number;
  name?: string | null;
  surname?: string | null;
  email?: string | null;
  password?: string | null;
  license?: string | null;
}

export type NewPerson = Omit<IPerson, 'id'> & { id: null };
