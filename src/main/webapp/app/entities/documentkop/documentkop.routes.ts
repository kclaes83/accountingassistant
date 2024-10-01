import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DocumentkopResolve from './route/documentkop-routing-resolve.service';

const documentkopRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/documentkop.component').then(m => m.DocumentkopComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/documentkop-detail.component').then(m => m.DocumentkopDetailComponent),
    resolve: {
      documentkop: DocumentkopResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/documentkop-update.component').then(m => m.DocumentkopUpdateComponent),
    resolve: {
      documentkop: DocumentkopResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/documentkop-update.component').then(m => m.DocumentkopUpdateComponent),
    resolve: {
      documentkop: DocumentkopResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default documentkopRoute;
