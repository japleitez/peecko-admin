import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVideoStat } from '../video-stat.model';
import { VideoStatService } from '../service/video-stat.service';

export const videoStatResolve = (route: ActivatedRouteSnapshot): Observable<null | IVideoStat> => {
  const id = route.params['id'];
  if (id) {
    return inject(VideoStatService)
      .find(id)
      .pipe(
        mergeMap((videoStat: HttpResponse<IVideoStat>) => {
          if (videoStat.body) {
            return of(videoStat.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default videoStatResolve;
