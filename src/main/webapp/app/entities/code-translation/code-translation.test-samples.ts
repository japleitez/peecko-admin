import { ICodeTranslation, NewCodeTranslation } from './code-translation.model';

export const sampleWithRequiredData: ICodeTranslation = {
  id: 1773,
  code: 'complicity mean',
  lang: 'EN',
  translation: 'unless whether darling',
};

export const sampleWithPartialData: ICodeTranslation = {
  id: 13911,
  code: 'call',
  lang: 'FR',
  translation: 'outrank until geez',
};

export const sampleWithFullData: ICodeTranslation = {
  id: 19779,
  code: 'complete',
  lang: 'DE',
  translation: 'yippee given',
};

export const sampleWithNewData: NewCodeTranslation = {
  code: 'spherical amuse',
  lang: 'EN',
  translation: 'coolly inasmuch knavishly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
