import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'aps-account',
        data: { pageTitle: 'ApsAccounts' },
        loadChildren: () => import('./aps-account/aps-account.routes'),
      },
      {
        path: 'secured-request',
        data: { pageTitle: 'SecuredRequests' },
        loadChildren: () => import('./secured-request/secured-request.routes'),
      },
      {
        path: 'aps-device',
        data: { pageTitle: 'ApsDevices' },
        loadChildren: () => import('./aps-device/aps-device.routes'),
      },
      {
        path: 'agency',
        data: { pageTitle: 'Agencies' },
        loadChildren: () => import('./agency/agency.routes'),
      },
      {
        path: 'staff',
        data: { pageTitle: 'Staff' },
        loadChildren: () => import('./staff/staff.routes'),
      },
      {
        path: 'customer',
        data: { pageTitle: 'Customers' },
        loadChildren: () => import('./customer/customer.routes'),
      },
      {
        path: 'contact',
        data: { pageTitle: 'Contacts' },
        loadChildren: () => import('./contact/contact.routes'),
      },
      {
        path: 'aps-plan',
        data: { pageTitle: 'ApsPlans' },
        loadChildren: () => import('./aps-plan/aps-plan.routes'),
      },
      {
        path: 'aps-order',
        data: { pageTitle: 'ApsOrders' },
        loadChildren: () => import('./aps-order/aps-order.routes'),
      },
      {
        path: 'aps-membership',
        data: { pageTitle: 'ApsMemberships' },
        loadChildren: () => import('./aps-membership/aps-membership.routes'),
      },
      {
        path: 'aps-pricing',
        data: { pageTitle: 'ApsPricings' },
        loadChildren: () => import('./aps-pricing/aps-pricing.routes'),
      },
      {
        path: 'video',
        data: { pageTitle: 'Videos' },
        loadChildren: () => import('./video/video.routes'),
      },
      {
        path: 'video-category',
        data: { pageTitle: 'VideoCategories' },
        loadChildren: () => import('./video-category/video-category.routes'),
      },
      {
        path: 'notification',
        data: { pageTitle: 'Notifications' },
        loadChildren: () => import('./notification/notification.routes'),
      },
      {
        path: 'code-translation',
        data: { pageTitle: 'CodeTranslations' },
        loadChildren: () => import('./code-translation/code-translation.routes'),
      },
      {
        path: 'playlist',
        data: { pageTitle: 'Playlists' },
        loadChildren: () => import('./playlist/playlist.routes'),
      },
      {
        path: 'video-item',
        data: { pageTitle: 'VideoItems' },
        loadChildren: () => import('./video-item/video-item.routes'),
      },
      {
        path: 'coach',
        data: { pageTitle: 'Coaches' },
        loadChildren: () => import('./coach/coach.routes'),
      },
      {
        path: 'invoice',
        data: { pageTitle: 'Invoices' },
        loadChildren: () => import('./invoice/invoice.routes'),
      },
      {
        path: 'invoice-item',
        data: { pageTitle: 'InvoiceItems' },
        loadChildren: () => import('./invoice-item/invoice-item.routes'),
      },
      {
        path: 'video-stat',
        data: { pageTitle: 'VideoStats' },
        loadChildren: () => import('./video-stat/video-stat.routes'),
      },
      {
        path: 'liked-video',
        data: { pageTitle: 'LikedVideos' },
        loadChildren: () => import('./liked-video/liked-video.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
