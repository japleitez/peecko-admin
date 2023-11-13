import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlaylist } from '../playlist.model';
import { PlaylistService } from '../service/playlist.service';

export const playlistResolve = (route: ActivatedRouteSnapshot): Observable<null | IPlaylist> => {
  const id = route.params['id'];
  if (id) {
    return inject(PlaylistService)
      .find(id)
      .pipe(
        mergeMap((playlist: HttpResponse<IPlaylist>) => {
          if (playlist.body) {
            return of(playlist.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default playlistResolve;
