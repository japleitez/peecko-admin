import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPlaylist } from 'app/entities/playlist/playlist.model';
import { PlaylistService } from 'app/entities/playlist/service/playlist.service';
import { IVideoItem } from '../video-item.model';
import { VideoItemService } from '../service/video-item.service';
import { VideoItemFormService, VideoItemFormGroup } from './video-item-form.service';

@Component({
  standalone: true,
  selector: 'jhi-video-item-update',
  templateUrl: './video-item-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VideoItemUpdateComponent implements OnInit {
  isSaving = false;
  videoItem: IVideoItem | null = null;

  playlistsSharedCollection: IPlaylist[] = [];

  editForm: VideoItemFormGroup = this.videoItemFormService.createVideoItemFormGroup();

  constructor(
    protected videoItemService: VideoItemService,
    protected videoItemFormService: VideoItemFormService,
    protected playlistService: PlaylistService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  comparePlaylist = (o1: IPlaylist | null, o2: IPlaylist | null): boolean => this.playlistService.comparePlaylist(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ videoItem }) => {
      this.videoItem = videoItem;
      if (videoItem) {
        this.updateForm(videoItem);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const videoItem = this.videoItemFormService.getVideoItem(this.editForm);
    if (videoItem.id !== null) {
      this.subscribeToSaveResponse(this.videoItemService.update(videoItem));
    } else {
      this.subscribeToSaveResponse(this.videoItemService.create(videoItem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideoItem>>): void {
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

  protected updateForm(videoItem: IVideoItem): void {
    this.videoItem = videoItem;
    this.videoItemFormService.resetForm(this.editForm, videoItem);

    this.playlistsSharedCollection = this.playlistService.addPlaylistToCollectionIfMissing<IPlaylist>(
      this.playlistsSharedCollection,
      videoItem.playlist,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.playlistService
      .query()
      .pipe(map((res: HttpResponse<IPlaylist[]>) => res.body ?? []))
      .pipe(
        map((playlists: IPlaylist[]) =>
          this.playlistService.addPlaylistToCollectionIfMissing<IPlaylist>(playlists, this.videoItem?.playlist),
        ),
      )
      .subscribe((playlists: IPlaylist[]) => (this.playlistsSharedCollection = playlists));
  }
}
