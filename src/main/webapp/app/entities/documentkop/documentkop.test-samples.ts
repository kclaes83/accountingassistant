import dayjs from 'dayjs/esm';

import { IDocumentkop, NewDocumentkop } from './documentkop.model';

export const sampleWithRequiredData: IDocumentkop = {
  id: 19309,
};

export const sampleWithPartialData: IDocumentkop = {
  id: 21049,
  bedrijfsnummer: 'faci',
  documentNrBoekhoudingsdocument: 'toward pin',
  boekjaar: 'woot',
  boekmaand: 'JA',
};

export const sampleWithFullData: IDocumentkop = {
  id: 19947,
  bedrijfsnummer: 'yak ',
  documentNrBoekhoudingsdocument: 'squid boas',
  boekjaar: 'bene',
  documentsoort: 'INKOMENDE_FACTUUR',
  documentdatum: dayjs('2024-10-01T06:35'),
  boekingsdatum: dayjs('2024-09-30T13:59'),
  boekmaand: 'MA',
  invoerdag: dayjs('2024-09-30T14:59'),
  invoertijd: dayjs('2024-09-30T22:46'),
};

export const sampleWithNewData: NewDocumentkop = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
