import dayjs from 'dayjs/esm';
import { IDocumentkop } from 'app/entities/documentkop/documentkop.model';

export interface IDocumentsegment {
  id: number;
  bedrijfsnummer?: string | null;
  documentNrBoekhoudingsdocument?: string | null;
  boekjaar?: string | null;
  boekingsregelNrBoekhoudingsdocument?: string | null;
  boekingsregelIdentificatie?: string | null;
  vereffeningsdatum?: dayjs.Dayjs | null;
  invoerdatumVereffening?: dayjs.Dayjs | null;
  vereffeningsdocumentNr?: string | null;
  boekingssleutel?: string | null;
  documentkop?: IDocumentkop | null;
}

export type NewDocumentsegment = Omit<IDocumentsegment, 'id'> & { id: null };
