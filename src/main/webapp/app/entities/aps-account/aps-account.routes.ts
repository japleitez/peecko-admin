import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ApsAccountComponent } from './list/aps-account.component';
import { ApsAccountDetailComponent } from './detail/aps-account-detail.component';
import { ApsAccountUpdateComponent } from './update/aps-account-update.component';
import ApsAccountResolve from './route/aps-account-routing-resolve.service';

const apsAccountRoute: Routes = [
  {
    path: '',
    component: ApsAccountComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApsAccountDetailComponent,
    resolve: {
      apsAccount: ApsAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApsAccountUpdateComponent,
    resolve: {
      apsAccount: ApsAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApsAccountUpdateComponent,
    resolve: {
      apsAccount: ApsAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default apsAccountRoute;
