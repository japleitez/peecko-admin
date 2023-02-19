import { CategoryType } from 'app/entities/enumerations/category-type.model';
import { LiveState } from 'app/entities/enumerations/live-state.model';

import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 2529,
  code: 'Namibia Sausages',
  type: CategoryType['WELLNESS'],
  state: LiveState['CREATED'],
  name: 'USB Down-sized Principal',
};

export const sampleWithPartialData: ICategory = {
  id: 82331,
  code: 'task-force Garden target',
  type: CategoryType['FITNESS'],
  state: LiveState['ARCHIVED'],
  name: 'Handmade hack Rustic',
};

export const sampleWithFullData: ICategory = {
  id: 72591,
  code: 'Licensed Account',
  type: CategoryType['WELLNESS'],
  state: LiveState['ARCHIVED'],
  name: 'Avon Group',
  label: 'Shoals hub withdrawal',
};

export const sampleWithNewData: NewCategory = {
  code: 'benchmark',
  type: CategoryType['FITNESS'],
  state: LiveState['CREATED'],
  name: 'Frozen',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
