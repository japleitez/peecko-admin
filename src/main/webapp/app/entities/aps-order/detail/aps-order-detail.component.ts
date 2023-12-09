import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IApsOrder } from '../aps-order.model';
import {ApsMembershipService} from "../../aps-membership/service/aps-membership.service";

@Component({
  standalone: true,
  selector: 'jhi-aps-order-detail',
  templateUrl: './aps-order-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ApsOrderDetailComponent {
  @Input() apsOrder: IApsOrder | null = null;
  file: File | null = null;

  constructor(protected apsMembershipService: ApsMembershipService, protected activatedRoute: ActivatedRoute) {}

  onFilechange(event: any) {
    console.log(event.target.files[0])
    this.file = event.target.files[0]
  }

  upload() {
    if (this.file && this.apsOrder && this.apsOrder.id) {
      const formData = new FormData();
      formData.append('membershipFile', this.file);
      formData.append("orderId", this.apsOrder.id.toString());
      this.apsMembershipService.uploadMembershipFile(formData).subscribe(
        (response) => {
        alert("File was uploaded " + response.status);
      });
    } else {
      alert("Please select a file first")
    }
  }

  previousState(): void {
    window.history.back();
  }
}
