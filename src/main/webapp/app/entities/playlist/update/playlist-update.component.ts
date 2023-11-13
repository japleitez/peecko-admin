import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IApsAccount } from 'app/entities/aps-account/aps-account.model';
import { ApsAccountService } from 'app/entities/aps-account/service/aps-account.service';
import { IPlaylist } from '../playlist.model';
import { PlaylistService } from '../service/playlist.service';
import { PlaylistFormService, PlaylistFormGroup } from './playlist-form.service';

@Component({
  standalone: true,
  selector: 'jhi-playlist-update',
  templateUrl: './playlist-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PlaylistUpdateComponent implements OnInit {
  isSaving = false;
  playlist: IPlaylist | null = null;

  apsAccountsSharedCollection: IApsAccount[] = [];

  editForm: PlaylistFormGroup = this.playlistFormService.createPlaylistFormGroup();

  constructor(
    protected playlistService: PlaylistService,
    protected playlistFormService: PlaylistFormService,
    protected apsAccountService: ApsAccountService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareApsAccount = (o1: IApsAccount | null, o2: IApsAccount | null): boolean => this.apsAccountService.compareApsAccount(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ playlist }) => {
      this.playlist = playlist;
      if (playlist) {
        this.updateForm(playlist);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const playlist = this.playlistFormService.getPlaylist(this.editForm);
    if (playlist.id !== null) {
      this.subscribeToSaveResponse(this.playlistService.update(playlist));
    } else {
      this.subscribeToSaveResponse(this.playlistService.create(playlist));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlaylist>>): void {
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

  protected updateForm(playlist: IPlaylist): void {
    this.playlist = playlist;
    this.playlistFormService.resetForm(this.editForm, playlist);

    this.apsAccountsSharedCollection = this.apsAccountService.addApsAccountToCollectionIfMissing<IApsAccount>(
      this.apsAccountsSharedCollection,
      playlist.apsAccount,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.apsAccountService
      .query()
      .pipe(map((res: HttpResponse<IApsAccount[]>) => res.body ?? []))
      .pipe(
        map((apsAccounts: IApsAccount[]) =>
          this.apsAccountService.addApsAccountToCollectionIfMissing<IApsAccount>(apsAccounts, this.playlist?.apsAccount),
        ),
      )
      .subscribe((apsAccounts: IApsAccount[]) => (this.apsAccountsSharedCollection = apsAccounts));
  }
}
