import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICodeTranslation } from '../code-translation.model';
import { CodeTranslationService } from '../service/code-translation.service';

@Component({
  standalone: true,
  templateUrl: './code-translation-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CodeTranslationDeleteDialogComponent {
  codeTranslation?: ICodeTranslation;

  constructor(
    protected codeTranslationService: CodeTranslationService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.codeTranslationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
