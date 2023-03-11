import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AddressDetailComponent } from './detail/address-detail.component';

@NgModule({
  imports: [SharedModule],
  declarations: [AddressDetailComponent],
})
export class AddressModule {}
