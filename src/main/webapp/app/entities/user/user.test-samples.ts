import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 14081,
  login: 'mZ@pqz',
};

export const sampleWithPartialData: IUser = {
  id: 11145,
  login: 'p=@d1lj\\?Z\\ImWbKhl\\^tObcd',
};

export const sampleWithFullData: IUser = {
  id: 23999,
  login: 'DvRr@1W\\4TA\\ys\\_eysZ',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
