import dayjs from 'dayjs/esm';
import { IApsOrder } from 'app/entities/aps-order/aps-order.model';

export interface IInvoice {
  id: number;
  number?: string | null;
  issued?: dayjs.Dayjs | null;
  dueDate?: dayjs.Dayjs | null;
  saleDate?: dayjs.Dayjs | null;
  subtotal?: number | null;
  vat?: number | null;
  total?: number | null;
  paid?: number | null;
  paidDate?: dayjs.Dayjs | null;
  diff?: number | null;
  notes?: string | null;
  apsOrder?: Pick<IApsOrder, 'id'> | null;
}

export type NewInvoice = Omit<IInvoice, 'id'> & { id: null };
