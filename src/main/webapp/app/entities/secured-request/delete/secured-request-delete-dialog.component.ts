import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISecuredRequest } from '../secured-request.model';
import { SecuredRequestService } from '../service/secured-request.service';

@Component({
  standalone: true,
  templateUrl: './secured-request-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SecuredRequestDeleteDialogComponent {
  securedRequest?: ISecuredRequest;

  constructor(
    protected securedRequestService: SecuredRequestService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.securedRequestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
