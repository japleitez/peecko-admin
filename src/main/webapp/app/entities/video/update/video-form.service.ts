import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IVideo, NewVideo } from '../video.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVideo for edit and NewVideoFormGroupInput for create.
 */
type VideoFormGroupInput = IVideo | PartialWithRequiredKeyOf<NewVideo>;

type VideoFormDefaults = Pick<NewVideo, 'id'>;

type VideoFormGroupContent = {
  id: FormControl<IVideo['id'] | NewVideo['id']>;
  code: FormControl<IVideo['code']>;
  state: FormControl<IVideo['state']>;
  name: FormControl<IVideo['name']>;
  tags: FormControl<IVideo['tags']>;
  lang: FormControl<IVideo['lang']>;
  url: FormControl<IVideo['url']>;
  category: FormControl<IVideo['category']>;
  coach: FormControl<IVideo['coach']>;
};

export type VideoFormGroup = FormGroup<VideoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VideoFormService {
  createVideoFormGroup(video: VideoFormGroupInput = { id: null }): VideoFormGroup {
    const videoRawValue = {
      ...this.getFormDefaults(),
      ...video,
    };
    return new FormGroup<VideoFormGroupContent>({
      id: new FormControl(
        { value: videoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(videoRawValue.code, {
        validators: [Validators.required],
      }),
      state: new FormControl(videoRawValue.state, {
        validators: [Validators.required],
      }),
      name: new FormControl(videoRawValue.name, {
        validators: [Validators.required],
      }),
      tags: new FormControl(videoRawValue.tags, {
        validators: [Validators.required],
      }),
      lang: new FormControl(videoRawValue.lang, {
        validators: [Validators.required],
      }),
      url: new FormControl(videoRawValue.url, {
        validators: [Validators.required],
      }),
      category: new FormControl(videoRawValue.category),
      coach: new FormControl(videoRawValue.coach),
    });
  }

  getVideo(form: VideoFormGroup): IVideo | NewVideo {
    return form.getRawValue() as IVideo | NewVideo;
  }

  resetForm(form: VideoFormGroup, video: VideoFormGroupInput): void {
    const videoRawValue = { ...this.getFormDefaults(), ...video };
    form.reset(
      {
        ...videoRawValue,
        id: { value: videoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): VideoFormDefaults {
    return {
      id: null,
    };
  }
}
