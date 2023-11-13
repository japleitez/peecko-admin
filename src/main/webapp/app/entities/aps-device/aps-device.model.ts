import dayjs from 'dayjs/esm';
import { IApsAccount } from 'app/entities/aps-account/aps-account.model';

export interface IApsDevice {
  id: number;
  username?: string | null;
  deviceId?: string | null;
  phoneModel?: string | null;
  osVersion?: string | null;
  installedOn?: dayjs.Dayjs | null;
  apsAccount?: Pick<IApsAccount, 'id'> | null;
}

export type NewApsDevice = Omit<IApsDevice, 'id'> & { id: null };
