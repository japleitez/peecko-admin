import dayjs from 'dayjs/esm';

export interface ISecuredRequest {
  id: number;
  requestId?: string | null;
  pinCode?: string | null;
  email?: string | null;
  created?: dayjs.Dayjs | null;
  expires?: dayjs.Dayjs | null;
}

export type NewSecuredRequest = Omit<ISecuredRequest, 'id'> & { id: null };
