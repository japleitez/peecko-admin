import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PlaylistComponent } from './list/playlist.component';
import { PlaylistDetailComponent } from './detail/playlist-detail.component';
import { PlaylistUpdateComponent } from './update/playlist-update.component';
import PlaylistResolve from './route/playlist-routing-resolve.service';

const playlistRoute: Routes = [
  {
    path: '',
    component: PlaylistComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlaylistDetailComponent,
    resolve: {
      playlist: PlaylistResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlaylistUpdateComponent,
    resolve: {
      playlist: PlaylistResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlaylistUpdateComponent,
    resolve: {
      playlist: PlaylistResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default playlistRoute;
