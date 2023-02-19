import { Country } from 'app/entities/enumerations/country.model';
import { ClientState } from 'app/entities/enumerations/client-state.model';

import { ICompany, NewCompany } from './company.model';

export const sampleWithRequiredData: ICompany = {
  id: 32440,
  code: 'Djibouti Washington',
  name: 'Awesome Avon Avon',
  country: Country['LU'],
  state: ClientState['TRIAL'],
  license: 'visualize Belize',
};

export const sampleWithPartialData: ICompany = {
  id: 18147,
  code: 'contextually-based Ball',
  name: 'contingency',
  country: Country['DE'],
  state: ClientState['TRIAL'],
  license: 'Nebraska',
};

export const sampleWithFullData: ICompany = {
  id: 47834,
  code: 'panel RAM withdrawal',
  name: 'JSON payment syndicate',
  country: Country['LU'],
  state: ClientState['TRIAL'],
  license: 'Research aggregate neural',
  vatin: 'Utah models',
};

export const sampleWithNewData: NewCompany = {
  code: 'innovative',
  name: 'Response multi-byte',
  country: Country['FR'],
  state: ClientState['ACTIVE'],
  license: 'deliver Secured Unbranded',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
