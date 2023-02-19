import { IMembership, NewMembership } from './membership.model';

export const sampleWithRequiredData: IMembership = {
  id: 29813,
  period: 'even-keeled',
  license: 'maximize',
  email: 'Gwen_Mitchell26@gmail.com',
};

export const sampleWithPartialData: IMembership = {
  id: 62581,
  period: 'encoding neural-net Kyat',
  license: 'portals',
  email: 'Gilbert.Runolfsdottir@yahoo.com',
};

export const sampleWithFullData: IMembership = {
  id: 49498,
  period: 'hacking digital relationships',
  license: 'Practical',
  email: 'Ewell10@hotmail.com',
};

export const sampleWithNewData: NewMembership = {
  period: 'Cheese harness PCI',
  license: 'encryption Mills',
  email: 'Issac_Conn@hotmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
