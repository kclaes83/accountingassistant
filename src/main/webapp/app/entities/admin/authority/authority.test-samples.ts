import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '075ac1da-ae02-4720-bbd4-b840fbb2c64a',
};

export const sampleWithPartialData: IAuthority = {
  name: '1d25d69c-7718-4794-8f2c-3635cef1ada0',
};

export const sampleWithFullData: IAuthority = {
  name: '6e67ebd4-49d0-4aba-b2a7-7893dfe41e85',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
