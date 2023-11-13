import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IApsAccount } from '../aps-account.model';
import { ApsAccountService } from '../service/aps-account.service';

@Component({
  standalone: true,
  templateUrl: './aps-account-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ApsAccountDeleteDialogComponent {
  apsAccount?: IApsAccount;

  constructor(
    protected apsAccountService: ApsAccountService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.apsAccountService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
