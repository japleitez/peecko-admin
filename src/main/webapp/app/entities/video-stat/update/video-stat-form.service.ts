import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IVideoStat, NewVideoStat } from '../video-stat.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVideoStat for edit and NewVideoStatFormGroupInput for create.
 */
type VideoStatFormGroupInput = IVideoStat | PartialWithRequiredKeyOf<NewVideoStat>;

type VideoStatFormDefaults = Pick<NewVideoStat, 'id'>;

type VideoStatFormGroupContent = {
  id: FormControl<IVideoStat['id'] | NewVideoStat['id']>;
  videoId: FormControl<IVideoStat['videoId']>;
  categoryId: FormControl<IVideoStat['categoryId']>;
  coachId: FormControl<IVideoStat['coachId']>;
  liked: FormControl<IVideoStat['liked']>;
  viewed: FormControl<IVideoStat['viewed']>;
};

export type VideoStatFormGroup = FormGroup<VideoStatFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VideoStatFormService {
  createVideoStatFormGroup(videoStat: VideoStatFormGroupInput = { id: null }): VideoStatFormGroup {
    const videoStatRawValue = {
      ...this.getFormDefaults(),
      ...videoStat,
    };
    return new FormGroup<VideoStatFormGroupContent>({
      id: new FormControl(
        { value: videoStatRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      videoId: new FormControl(videoStatRawValue.videoId, {
        validators: [Validators.required],
      }),
      categoryId: new FormControl(videoStatRawValue.categoryId, {
        validators: [Validators.required],
      }),
      coachId: new FormControl(videoStatRawValue.coachId, {
        validators: [Validators.required],
      }),
      liked: new FormControl(videoStatRawValue.liked, {
        validators: [Validators.required],
      }),
      viewed: new FormControl(videoStatRawValue.viewed, {
        validators: [Validators.required],
      }),
    });
  }

  getVideoStat(form: VideoStatFormGroup): IVideoStat | NewVideoStat {
    return form.getRawValue() as IVideoStat | NewVideoStat;
  }

  resetForm(form: VideoStatFormGroup, videoStat: VideoStatFormGroupInput): void {
    const videoStatRawValue = { ...this.getFormDefaults(), ...videoStat };
    form.reset(
      {
        ...videoStatRawValue,
        id: { value: videoStatRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VideoStatFormDefaults {
    return {
      id: null,
    };
  }
}
