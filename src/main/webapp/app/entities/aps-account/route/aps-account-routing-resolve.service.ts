import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApsAccount } from '../aps-account.model';
import { ApsAccountService } from '../service/aps-account.service';

export const apsAccountResolve = (route: ActivatedRouteSnapshot): Observable<null | IApsAccount> => {
  const id = route.params['id'];
  if (id) {
    return inject(ApsAccountService)
      .find(id)
      .pipe(
        mergeMap((apsAccount: HttpResponse<IApsAccount>) => {
          if (apsAccount.body) {
            return of(apsAccount.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default apsAccountResolve;
