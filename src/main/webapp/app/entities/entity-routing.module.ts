import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company',
        data: { pageTitle: 'Companies' },
        loadChildren: () => import('./company/company.module').then(m => m.CompanyModule),
      },
      {
        path: 'plan',
        data: { pageTitle: 'Plans' },
        loadChildren: () => import('./plan/plan.module').then(m => m.PlanModule),
      },
      {
        path: 'membership',
        data: { pageTitle: 'Memberships' },
        loadChildren: () => import('./membership/membership.module').then(m => m.MembershipModule),
      },
      {
        path: 'contact',
        data: { pageTitle: 'Contacts' },
        loadChildren: () => import('./contact/contact.module').then(m => m.ContactModule),
      },
      {
        path: 'person',
        data: { pageTitle: 'People' },
        loadChildren: () => import('./person/person.module').then(m => m.PersonModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'Categories' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'video',
        data: { pageTitle: 'Videos' },
        loadChildren: () => import('./video/video.module').then(m => m.VideoModule),
      },
      {
        path: 'coach',
        data: { pageTitle: 'Coaches' },
        loadChildren: () => import('./coach/coach.module').then(m => m.CoachModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
