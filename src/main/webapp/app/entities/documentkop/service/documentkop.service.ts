import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocumentkop, NewDocumentkop } from '../documentkop.model';

export type PartialUpdateDocumentkop = Partial<IDocumentkop> & Pick<IDocumentkop, 'id'>;

type RestOf<T extends IDocumentkop | NewDocumentkop> = Omit<T, 'documentdatum' | 'boekingsdatum' | 'invoerdag' | 'invoertijd'> & {
  documentdatum?: string | null;
  boekingsdatum?: string | null;
  invoerdag?: string | null;
  invoertijd?: string | null;
};

export type RestDocumentkop = RestOf<IDocumentkop>;

export type NewRestDocumentkop = RestOf<NewDocumentkop>;

export type PartialUpdateRestDocumentkop = RestOf<PartialUpdateDocumentkop>;

export type EntityResponseType = HttpResponse<IDocumentkop>;
export type EntityArrayResponseType = HttpResponse<IDocumentkop[]>;

@Injectable({ providedIn: 'root' })
export class DocumentkopService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/documentkops');

  create(documentkop: NewDocumentkop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentkop);
    return this.http
      .post<RestDocumentkop>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(documentkop: IDocumentkop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentkop);
    return this.http
      .put<RestDocumentkop>(`${this.resourceUrl}/${this.getDocumentkopIdentifier(documentkop)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(documentkop: PartialUpdateDocumentkop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documentkop);
    return this.http
      .patch<RestDocumentkop>(`${this.resourceUrl}/${this.getDocumentkopIdentifier(documentkop)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDocumentkop>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDocumentkop[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDocumentkopIdentifier(documentkop: Pick<IDocumentkop, 'id'>): number {
    return documentkop.id;
  }

  compareDocumentkop(o1: Pick<IDocumentkop, 'id'> | null, o2: Pick<IDocumentkop, 'id'> | null): boolean {
    return o1 && o2 ? this.getDocumentkopIdentifier(o1) === this.getDocumentkopIdentifier(o2) : o1 === o2;
  }

  addDocumentkopToCollectionIfMissing<Type extends Pick<IDocumentkop, 'id'>>(
    documentkopCollection: Type[],
    ...documentkopsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const documentkops: Type[] = documentkopsToCheck.filter(isPresent);
    if (documentkops.length > 0) {
      const documentkopCollectionIdentifiers = documentkopCollection.map(documentkopItem => this.getDocumentkopIdentifier(documentkopItem));
      const documentkopsToAdd = documentkops.filter(documentkopItem => {
        const documentkopIdentifier = this.getDocumentkopIdentifier(documentkopItem);
        if (documentkopCollectionIdentifiers.includes(documentkopIdentifier)) {
          return false;
        }
        documentkopCollectionIdentifiers.push(documentkopIdentifier);
        return true;
      });
      return [...documentkopsToAdd, ...documentkopCollection];
    }
    return documentkopCollection;
  }

  protected convertDateFromClient<T extends IDocumentkop | NewDocumentkop | PartialUpdateDocumentkop>(documentkop: T): RestOf<T> {
    return {
      ...documentkop,
      documentdatum: documentkop.documentdatum?.toJSON() ?? null,
      boekingsdatum: documentkop.boekingsdatum?.toJSON() ?? null,
      invoerdag: documentkop.invoerdag?.toJSON() ?? null,
      invoertijd: documentkop.invoertijd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDocumentkop: RestDocumentkop): IDocumentkop {
    return {
      ...restDocumentkop,
      documentdatum: restDocumentkop.documentdatum ? dayjs(restDocumentkop.documentdatum) : undefined,
      boekingsdatum: restDocumentkop.boekingsdatum ? dayjs(restDocumentkop.boekingsdatum) : undefined,
      invoerdag: restDocumentkop.invoerdag ? dayjs(restDocumentkop.invoerdag) : undefined,
      invoertijd: restDocumentkop.invoertijd ? dayjs(restDocumentkop.invoertijd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDocumentkop>): HttpResponse<IDocumentkop> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDocumentkop[]>): HttpResponse<IDocumentkop[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
