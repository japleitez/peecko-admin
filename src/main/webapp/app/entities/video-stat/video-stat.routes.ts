import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { VideoStatComponent } from './list/video-stat.component';
import { VideoStatDetailComponent } from './detail/video-stat-detail.component';
import { VideoStatUpdateComponent } from './update/video-stat-update.component';
import VideoStatResolve from './route/video-stat-routing-resolve.service';

const videoStatRoute: Routes = [
  {
    path: '',
    component: VideoStatComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VideoStatDetailComponent,
    resolve: {
      videoStat: VideoStatResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VideoStatUpdateComponent,
    resolve: {
      videoStat: VideoStatResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VideoStatUpdateComponent,
    resolve: {
      videoStat: VideoStatResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default videoStatRoute;
