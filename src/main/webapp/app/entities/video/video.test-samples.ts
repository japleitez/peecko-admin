import { LiveState } from 'app/entities/enumerations/live-state.model';
import { Lang } from 'app/entities/enumerations/lang.model';

import { IVideo, NewVideo } from './video.model';

export const sampleWithRequiredData: IVideo = {
  id: 25330,
  code: 'users',
  state: LiveState['PUBLISHED'],
  name: 'Comoros Prairie',
  tags: 'Keyboard',
  lang: Lang['FR'],
  url: 'https://dion.net',
};

export const sampleWithPartialData: IVideo = {
  id: 70764,
  code: 'Idaho secured',
  state: LiveState['CREATED'],
  name: 'Fresh application Car',
  tags: 'compress modular compressing',
  lang: Lang['DE'],
  url: 'http://myrtie.net',
};

export const sampleWithFullData: IVideo = {
  id: 6575,
  code: 'synthesizing hack',
  state: LiveState['CREATED'],
  name: 'Generic Michigan extensible',
  tags: 'multi-byte Republic',
  lang: Lang['DE'],
  url: 'https://dahlia.com',
};

export const sampleWithNewData: NewVideo = {
  code: 'Generic',
  state: LiveState['ARCHIVED'],
  name: 'revolutionary',
  tags: 'interactive',
  lang: Lang['FR'],
  url: 'https://angel.name',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
