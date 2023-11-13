import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SecuredRequestComponent } from './list/secured-request.component';
import { SecuredRequestDetailComponent } from './detail/secured-request-detail.component';
import { SecuredRequestUpdateComponent } from './update/secured-request-update.component';
import SecuredRequestResolve from './route/secured-request-routing-resolve.service';

const securedRequestRoute: Routes = [
  {
    path: '',
    component: SecuredRequestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SecuredRequestDetailComponent,
    resolve: {
      securedRequest: SecuredRequestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SecuredRequestUpdateComponent,
    resolve: {
      securedRequest: SecuredRequestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SecuredRequestUpdateComponent,
    resolve: {
      securedRequest: SecuredRequestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default securedRequestRoute;
