import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DocumentsegmentResolve from './route/documentsegment-routing-resolve.service';

const documentsegmentRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/documentsegment.component').then(m => m.DocumentsegmentComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/documentsegment-detail.component').then(m => m.DocumentsegmentDetailComponent),
    resolve: {
      documentsegment: DocumentsegmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/documentsegment-update.component').then(m => m.DocumentsegmentUpdateComponent),
    resolve: {
      documentsegment: DocumentsegmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/documentsegment-update.component').then(m => m.DocumentsegmentUpdateComponent),
    resolve: {
      documentsegment: DocumentsegmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default documentsegmentRoute;
