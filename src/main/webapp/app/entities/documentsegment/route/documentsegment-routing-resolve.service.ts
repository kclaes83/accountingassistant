import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocumentsegment } from '../documentsegment.model';
import { DocumentsegmentService } from '../service/documentsegment.service';

const documentsegmentResolve = (route: ActivatedRouteSnapshot): Observable<null | IDocumentsegment> => {
  const id = route.params.id;
  if (id) {
    return inject(DocumentsegmentService)
      .find(id)
      .pipe(
        mergeMap((documentsegment: HttpResponse<IDocumentsegment>) => {
          if (documentsegment.body) {
            return of(documentsegment.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default documentsegmentResolve;
