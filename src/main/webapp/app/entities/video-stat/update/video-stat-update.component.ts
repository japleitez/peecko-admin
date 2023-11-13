import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IVideoStat } from '../video-stat.model';
import { VideoStatService } from '../service/video-stat.service';
import { VideoStatFormService, VideoStatFormGroup } from './video-stat-form.service';

@Component({
  standalone: true,
  selector: 'jhi-video-stat-update',
  templateUrl: './video-stat-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VideoStatUpdateComponent implements OnInit {
  isSaving = false;
  videoStat: IVideoStat | null = null;

  editForm: VideoStatFormGroup = this.videoStatFormService.createVideoStatFormGroup();

  constructor(
    protected videoStatService: VideoStatService,
    protected videoStatFormService: VideoStatFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ videoStat }) => {
      this.videoStat = videoStat;
      if (videoStat) {
        this.updateForm(videoStat);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const videoStat = this.videoStatFormService.getVideoStat(this.editForm);
    if (videoStat.id !== null) {
      this.subscribeToSaveResponse(this.videoStatService.update(videoStat));
    } else {
      this.subscribeToSaveResponse(this.videoStatService.create(videoStat));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideoStat>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(videoStat: IVideoStat): void {
    this.videoStat = videoStat;
    this.videoStatFormService.resetForm(this.editForm, videoStat);
  }
}
