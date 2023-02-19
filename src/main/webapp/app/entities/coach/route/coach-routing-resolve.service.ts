import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICoach } from '../coach.model';
import { CoachService } from '../service/coach.service';

@Injectable({ providedIn: 'root' })
export class CoachRoutingResolveService implements Resolve<ICoach | null> {
  constructor(protected service: CoachService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICoach | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((coach: HttpResponse<ICoach>) => {
          if (coach.body) {
            return of(coach.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
