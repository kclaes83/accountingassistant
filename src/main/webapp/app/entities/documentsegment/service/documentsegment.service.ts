import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocumentsegment, NewDocumentsegment } from '../documentsegment.model';

export type PartialUpdateDocumentsegment = Partial<IDocumentsegment> & Pick<IDocumentsegment, 'id'>;

type RestOf<T extends IDocumentsegment | NewDocumentsegment> = Omit<T, 'vereffeningsdatum' | 'invoerdatumVereffening'> & {
  vereffeningsdatum?: string | null;
  invoerdatumVereffening?: string | null;
};

export type RestDocumentsegment = RestOf<IDocumentsegment>;

export type NewRestDocumentsegment = RestOf<NewDocumentsegment>;

export type PartialUpdateRestDocumentsegment = RestOf<PartialUpdateDocumentsegment>;

export type EntityResponseType = HttpResponse<IDocumentsegment>;
export type EntityArrayResponseType = HttpResponse<IDocumentsegment[]>;

@Injectable({ providedIn: 'root' })
export class DocumentsegmentService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/documentsegments');

  create(documentsegment: NewDocumentsegment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentsegment);
    return this.http
      .post<RestDocumentsegment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(documentsegment: IDocumentsegment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentsegment);
    return this.http
      .put<RestDocumentsegment>(`${this.resourceUrl}/${this.getDocumentsegmentIdentifier(documentsegment)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(documentsegment: PartialUpdateDocumentsegment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentsegment);
    return this.http
      .patch<RestDocumentsegment>(`${this.resourceUrl}/${this.getDocumentsegmentIdentifier(documentsegment)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDocumentsegment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDocumentsegment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDocumentsegmentIdentifier(documentsegment: Pick<IDocumentsegment, 'id'>): number {
    return documentsegment.id;
  }

  compareDocumentsegment(o1: Pick<IDocumentsegment, 'id'> | null, o2: Pick<IDocumentsegment, 'id'> | null): boolean {
    return o1 && o2 ? this.getDocumentsegmentIdentifier(o1) === this.getDocumentsegmentIdentifier(o2) : o1 === o2;
  }

  addDocumentsegmentToCollectionIfMissing<Type extends Pick<IDocumentsegment, 'id'>>(
    documentsegmentCollection: Type[],
    ...documentsegmentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const documentsegments: Type[] = documentsegmentsToCheck.filter(isPresent);
    if (documentsegments.length > 0) {
      const documentsegmentCollectionIdentifiers = documentsegmentCollection.map(documentsegmentItem =>
        this.getDocumentsegmentIdentifier(documentsegmentItem),
      );
      const documentsegmentsToAdd = documentsegments.filter(documentsegmentItem => {
        const documentsegmentIdentifier = this.getDocumentsegmentIdentifier(documentsegmentItem);
        if (documentsegmentCollectionIdentifiers.includes(documentsegmentIdentifier)) {
          return false;
        }
        documentsegmentCollectionIdentifiers.push(documentsegmentIdentifier);
        return true;
      });
      return [...documentsegmentsToAdd, ...documentsegmentCollection];
    }
    return documentsegmentCollection;
  }

  protected convertDateFromClient<T extends IDocumentsegment | NewDocumentsegment | PartialUpdateDocumentsegment>(
    documentsegment: T,
  ): RestOf<T> {
    return {
      ...documentsegment,
      vereffeningsdatum: documentsegment.vereffeningsdatum?.toJSON() ?? null,
      invoerdatumVereffening: documentsegment.invoerdatumVereffening?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDocumentsegment: RestDocumentsegment): IDocumentsegment {
    return {
      ...restDocumentsegment,
      vereffeningsdatum: restDocumentsegment.vereffeningsdatum ? dayjs(restDocumentsegment.vereffeningsdatum) : undefined,
      invoerdatumVereffening: restDocumentsegment.invoerdatumVereffening ? dayjs(restDocumentsegment.invoerdatumVereffening) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDocumentsegment>): HttpResponse<IDocumentsegment> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDocumentsegment[]>): HttpResponse<IDocumentsegment[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
