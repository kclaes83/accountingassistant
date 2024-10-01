import dayjs from 'dayjs/esm';

import { IDocumentsegment, NewDocumentsegment } from './documentsegment.model';

export const sampleWithRequiredData: IDocumentsegment = {
  id: 26800,
  boekingssleutel: 'ex',
};

export const sampleWithPartialData: IDocumentsegment = {
  id: 17182,
  bedrijfsnummer: 'low ',
  boekingsregelIdentificatie: 'f',
  boekingssleutel: 'ta',
};

export const sampleWithFullData: IDocumentsegment = {
  id: 24129,
  bedrijfsnummer: 'why',
  documentNrBoekhoudingsdocument: 'as',
  boekjaar: 'supp',
  boekingsregelNrBoekhoudingsdocument: 'emp',
  boekingsregelIdentificatie: 'g',
  vereffeningsdatum: dayjs('2024-10-01T08:08'),
  invoerdatumVereffening: dayjs('2024-10-01T02:20'),
  vereffeningsdocumentNr: 'while grat',
  boekingssleutel: 'wh',
};

export const sampleWithNewData: NewDocumentsegment = {
  boekingssleutel: 'pu',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
