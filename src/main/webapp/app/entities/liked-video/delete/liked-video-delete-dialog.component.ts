import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ILikedVideo } from '../liked-video.model';
import { LikedVideoService } from '../service/liked-video.service';

@Component({
  standalone: true,
  templateUrl: './liked-video-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class LikedVideoDeleteDialogComponent {
  likedVideo?: ILikedVideo;

  constructor(
    protected likedVideoService: LikedVideoService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.likedVideoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
