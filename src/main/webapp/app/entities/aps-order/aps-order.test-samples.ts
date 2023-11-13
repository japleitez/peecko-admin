import { IApsOrder, NewApsOrder } from './aps-order.model';

export const sampleWithRequiredData: IApsOrder = {
  id: 16448,
  contract: 'commerce wind',
  period: 3431,
  license: 'pop via',
  unitPrice: 19408.18,
  vatRate: 10171.06,
  numberOfUsers: 3451,
};

export const sampleWithPartialData: IApsOrder = {
  id: 8222,
  contract: 'um',
  period: 6644,
  license: 'plus stall',
  unitPrice: 19094.42,
  vatRate: 21515.68,
  numberOfUsers: 3024,
};

export const sampleWithFullData: IApsOrder = {
  id: 8447,
  contract: 'midden until whose',
  period: 21857,
  license: 'berate',
  unitPrice: 24484.77,
  vatRate: 19232.45,
  numberOfUsers: 10766,
  invoiceNumber: 'astride video justly',
};

export const sampleWithNewData: NewApsOrder = {
  contract: 'indeed',
  period: 31876,
  license: 'after',
  unitPrice: 28247.65,
  vatRate: 6421.3,
  numberOfUsers: 23746,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
