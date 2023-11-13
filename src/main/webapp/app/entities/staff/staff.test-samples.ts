import { IStaff, NewStaff } from './staff.model';

export const sampleWithRequiredData: IStaff = {
  id: 1113,
  staffId: 28955,
};

export const sampleWithPartialData: IStaff = {
  id: 11880,
  staffId: 19854,
};

export const sampleWithFullData: IStaff = {
  id: 10391,
  staffId: 1870,
};

export const sampleWithNewData: NewStaff = {
  staffId: 23891,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
