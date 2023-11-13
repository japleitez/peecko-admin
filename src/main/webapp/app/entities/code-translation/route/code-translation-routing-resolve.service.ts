import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICodeTranslation } from '../code-translation.model';
import { CodeTranslationService } from '../service/code-translation.service';

export const codeTranslationResolve = (route: ActivatedRouteSnapshot): Observable<null | ICodeTranslation> => {
  const id = route.params['id'];
  if (id) {
    return inject(CodeTranslationService)
      .find(id)
      .pipe(
        mergeMap((codeTranslation: HttpResponse<ICodeTranslation>) => {
          if (codeTranslation.body) {
            return of(codeTranslation.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default codeTranslationResolve;
