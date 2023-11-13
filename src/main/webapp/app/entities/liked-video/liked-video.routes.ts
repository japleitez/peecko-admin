import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { LikedVideoComponent } from './list/liked-video.component';
import { LikedVideoDetailComponent } from './detail/liked-video-detail.component';
import { LikedVideoUpdateComponent } from './update/liked-video-update.component';
import LikedVideoResolve from './route/liked-video-routing-resolve.service';

const likedVideoRoute: Routes = [
  {
    path: '',
    component: LikedVideoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LikedVideoDetailComponent,
    resolve: {
      likedVideo: LikedVideoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LikedVideoUpdateComponent,
    resolve: {
      likedVideo: LikedVideoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LikedVideoUpdateComponent,
    resolve: {
      likedVideo: LikedVideoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default likedVideoRoute;
