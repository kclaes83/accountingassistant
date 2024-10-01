import dayjs from 'dayjs/esm';
import { DocumentSoort } from 'app/entities/enumerations/document-soort.model';
import { Maand } from 'app/entities/enumerations/maand.model';

export interface IDocumentkop {
  id: number;
  bedrijfsnummer?: string | null;
  documentNrBoekhoudingsdocument?: string | null;
  boekjaar?: string | null;
  documentsoort?: keyof typeof DocumentSoort | null;
  documentdatum?: dayjs.Dayjs | null;
  boekingsdatum?: dayjs.Dayjs | null;
  boekmaand?: keyof typeof Maand | null;
  invoerdag?: dayjs.Dayjs | null;
  invoertijd?: dayjs.Dayjs | null;
}

export type NewDocumentkop = Omit<IDocumentkop, 'id'> & { id: null };
