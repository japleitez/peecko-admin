import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ILikedVideo } from '../liked-video.model';
import { LikedVideoService } from '../service/liked-video.service';
import { LikedVideoFormService, LikedVideoFormGroup } from './liked-video-form.service';

@Component({
  standalone: true,
  selector: 'jhi-liked-video-update',
  templateUrl: './liked-video-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class LikedVideoUpdateComponent implements OnInit {
  isSaving = false;
  likedVideo: ILikedVideo | null = null;

  editForm: LikedVideoFormGroup = this.likedVideoFormService.createLikedVideoFormGroup();

  constructor(
    protected likedVideoService: LikedVideoService,
    protected likedVideoFormService: LikedVideoFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ likedVideo }) => {
      this.likedVideo = likedVideo;
      if (likedVideo) {
        this.updateForm(likedVideo);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const likedVideo = this.likedVideoFormService.getLikedVideo(this.editForm);
    if (likedVideo.id !== null) {
      this.subscribeToSaveResponse(this.likedVideoService.update(likedVideo));
    } else {
      this.subscribeToSaveResponse(this.likedVideoService.create(likedVideo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILikedVideo>>): void {
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

  protected updateForm(likedVideo: ILikedVideo): void {
    this.likedVideo = likedVideo;
    this.likedVideoFormService.resetForm(this.editForm, likedVideo);
  }
}
