import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILikedVideo } from '../liked-video.model';
import { LikedVideoService } from '../service/liked-video.service';

export const likedVideoResolve = (route: ActivatedRouteSnapshot): Observable<null | ILikedVideo> => {
  const id = route.params['id'];
  if (id) {
    return inject(LikedVideoService)
      .find(id)
      .pipe(
        mergeMap((likedVideo: HttpResponse<ILikedVideo>) => {
          if (likedVideo.body) {
            return of(likedVideo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default likedVideoResolve;
