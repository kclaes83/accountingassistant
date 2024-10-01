import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocumentkop } from '../documentkop.model';
import { DocumentkopService } from '../service/documentkop.service';

const documentkopResolve = (route: ActivatedRouteSnapshot): Observable<null | IDocumentkop> => {
  const id = route.params.id;
  if (id) {
    return inject(DocumentkopService)
      .find(id)
      .pipe(
        mergeMap((documentkop: HttpResponse<IDocumentkop>) => {
          if (documentkop.body) {
            return of(documentkop.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default documentkopResolve;
