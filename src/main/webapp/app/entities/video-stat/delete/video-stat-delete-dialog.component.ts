import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IVideoStat } from '../video-stat.model';
import { VideoStatService } from '../service/video-stat.service';

@Component({
  standalone: true,
  templateUrl: './video-stat-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VideoStatDeleteDialogComponent {
  videoStat?: IVideoStat;

  constructor(
    protected videoStatService: VideoStatService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.videoStatService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
