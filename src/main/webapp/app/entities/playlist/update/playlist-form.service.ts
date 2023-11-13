import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPlaylist, NewPlaylist } from '../playlist.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlaylist for edit and NewPlaylistFormGroupInput for create.
 */
type PlaylistFormGroupInput = IPlaylist | PartialWithRequiredKeyOf<NewPlaylist>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPlaylist | NewPlaylist> = Omit<T, 'created' | 'updated'> & {
  created?: string | null;
  updated?: string | null;
};

type PlaylistFormRawValue = FormValueOf<IPlaylist>;

type NewPlaylistFormRawValue = FormValueOf<NewPlaylist>;

type PlaylistFormDefaults = Pick<NewPlaylist, 'id' | 'created' | 'updated'>;

type PlaylistFormGroupContent = {
  id: FormControl<PlaylistFormRawValue['id'] | NewPlaylist['id']>;
  name: FormControl<PlaylistFormRawValue['name']>;
  counter: FormControl<PlaylistFormRawValue['counter']>;
  created: FormControl<PlaylistFormRawValue['created']>;
  updated: FormControl<PlaylistFormRawValue['updated']>;
  apsAccount: FormControl<PlaylistFormRawValue['apsAccount']>;
};

export type PlaylistFormGroup = FormGroup<PlaylistFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlaylistFormService {
  createPlaylistFormGroup(playlist: PlaylistFormGroupInput = { id: null }): PlaylistFormGroup {
    const playlistRawValue = this.convertPlaylistToPlaylistRawValue({
      ...this.getFormDefaults(),
      ...playlist,
    });
    return new FormGroup<PlaylistFormGroupContent>({
      id: new FormControl(
        { value: playlistRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(playlistRawValue.name, {
        validators: [Validators.required],
      }),
      counter: new FormControl(playlistRawValue.counter, {
        validators: [Validators.required],
      }),
      created: new FormControl(playlistRawValue.created, {
        validators: [Validators.required],
      }),
      updated: new FormControl(playlistRawValue.updated, {
        validators: [Validators.required],
      }),
      apsAccount: new FormControl(playlistRawValue.apsAccount),
    });
  }

  getPlaylist(form: PlaylistFormGroup): IPlaylist | NewPlaylist {
    return this.convertPlaylistRawValueToPlaylist(form.getRawValue() as PlaylistFormRawValue | NewPlaylistFormRawValue);
  }

  resetForm(form: PlaylistFormGroup, playlist: PlaylistFormGroupInput): void {
    const playlistRawValue = this.convertPlaylistToPlaylistRawValue({ ...this.getFormDefaults(), ...playlist });
    form.reset(
      {
        ...playlistRawValue,
        id: { value: playlistRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PlaylistFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      updated: currentTime,
    };
  }

  private convertPlaylistRawValueToPlaylist(rawPlaylist: PlaylistFormRawValue | NewPlaylistFormRawValue): IPlaylist | NewPlaylist {
    return {
      ...rawPlaylist,
      created: dayjs(rawPlaylist.created, DATE_TIME_FORMAT),
      updated: dayjs(rawPlaylist.updated, DATE_TIME_FORMAT),
    };
  }

  private convertPlaylistToPlaylistRawValue(
    playlist: IPlaylist | (Partial<NewPlaylist> & PlaylistFormDefaults),
  ): PlaylistFormRawValue | PartialWithRequiredKeyOf<NewPlaylistFormRawValue> {
    return {
      ...playlist,
      created: playlist.created ? playlist.created.format(DATE_TIME_FORMAT) : undefined,
      updated: playlist.updated ? playlist.updated.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
