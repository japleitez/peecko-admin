import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CodeTranslationComponent } from './list/code-translation.component';
import { CodeTranslationDetailComponent } from './detail/code-translation-detail.component';
import { CodeTranslationUpdateComponent } from './update/code-translation-update.component';
import CodeTranslationResolve from './route/code-translation-routing-resolve.service';

const codeTranslationRoute: Routes = [
  {
    path: '',
    component: CodeTranslationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CodeTranslationDetailComponent,
    resolve: {
      codeTranslation: CodeTranslationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CodeTranslationUpdateComponent,
    resolve: {
      codeTranslation: CodeTranslationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CodeTranslationUpdateComponent,
    resolve: {
      codeTranslation: CodeTranslationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default codeTranslationRoute;
