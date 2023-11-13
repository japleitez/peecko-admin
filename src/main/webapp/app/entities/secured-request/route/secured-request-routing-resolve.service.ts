import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISecuredRequest } from '../secured-request.model';
import { SecuredRequestService } from '../service/secured-request.service';

export const securedRequestResolve = (route: ActivatedRouteSnapshot): Observable<null | ISecuredRequest> => {
  const id = route.params['id'];
  if (id) {
    return inject(SecuredRequestService)
      .find(id)
      .pipe(
        mergeMap((securedRequest: HttpResponse<ISecuredRequest>) => {
          if (securedRequest.body) {
            return of(securedRequest.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default securedRequestResolve;
