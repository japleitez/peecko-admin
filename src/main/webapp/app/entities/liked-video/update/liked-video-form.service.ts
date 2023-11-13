import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILikedVideo, NewLikedVideo } from '../liked-video.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILikedVideo for edit and NewLikedVideoFormGroupInput for create.
 */
type LikedVideoFormGroupInput = ILikedVideo | PartialWithRequiredKeyOf<NewLikedVideo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILikedVideo | NewLikedVideo> = Omit<T, 'created'> & {
  created?: string | null;
};

type LikedVideoFormRawValue = FormValueOf<ILikedVideo>;

type NewLikedVideoFormRawValue = FormValueOf<NewLikedVideo>;

type LikedVideoFormDefaults = Pick<NewLikedVideo, 'id' | 'created'>;

type LikedVideoFormGroupContent = {
  id: FormControl<LikedVideoFormRawValue['id'] | NewLikedVideo['id']>;
  videoId: FormControl<LikedVideoFormRawValue['videoId']>;
  personId: FormControl<LikedVideoFormRawValue['personId']>;
  coachId: FormControl<LikedVideoFormRawValue['coachId']>;
  created: FormControl<LikedVideoFormRawValue['created']>;
};

export type LikedVideoFormGroup = FormGroup<LikedVideoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LikedVideoFormService {
  createLikedVideoFormGroup(likedVideo: LikedVideoFormGroupInput = { id: null }): LikedVideoFormGroup {
    const likedVideoRawValue = this.convertLikedVideoToLikedVideoRawValue({
      ...this.getFormDefaults(),
      ...likedVideo,
    });
    return new FormGroup<LikedVideoFormGroupContent>({
      id: new FormControl(
        { value: likedVideoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      videoId: new FormControl(likedVideoRawValue.videoId, {
        validators: [Validators.required],
      }),
      personId: new FormControl(likedVideoRawValue.personId, {
        validators: [Validators.required],
      }),
      coachId: new FormControl(likedVideoRawValue.coachId, {
        validators: [Validators.required],
      }),
      created: new FormControl(likedVideoRawValue.created, {
        validators: [Validators.required],
      }),
    });
  }

  getLikedVideo(form: LikedVideoFormGroup): ILikedVideo | NewLikedVideo {
    return this.convertLikedVideoRawValueToLikedVideo(form.getRawValue() as LikedVideoFormRawValue | NewLikedVideoFormRawValue);
  }

  resetForm(form: LikedVideoFormGroup, likedVideo: LikedVideoFormGroupInput): void {
    const likedVideoRawValue = this.convertLikedVideoToLikedVideoRawValue({ ...this.getFormDefaults(), ...likedVideo });
    form.reset(
      {
        ...likedVideoRawValue,
        id: { value: likedVideoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LikedVideoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
    };
  }

  private convertLikedVideoRawValueToLikedVideo(
    rawLikedVideo: LikedVideoFormRawValue | NewLikedVideoFormRawValue,
  ): ILikedVideo | NewLikedVideo {
    return {
      ...rawLikedVideo,
      created: dayjs(rawLikedVideo.created, DATE_TIME_FORMAT),
    };
  }

  private convertLikedVideoToLikedVideoRawValue(
    likedVideo: ILikedVideo | (Partial<NewLikedVideo> & LikedVideoFormDefaults),
  ): LikedVideoFormRawValue | PartialWithRequiredKeyOf<NewLikedVideoFormRawValue> {
    return {
      ...likedVideo,
      created: likedVideo.created ? likedVideo.created.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
