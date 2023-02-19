import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { VideoFormService, VideoFormGroup } from './video-form.service';
import { IVideo } from '../video.model';
import { VideoService } from '../service/video.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { ICoach } from 'app/entities/coach/coach.model';
import { CoachService } from 'app/entities/coach/service/coach.service';
import { LiveState } from 'app/entities/enumerations/live-state.model';
import { Lang } from 'app/entities/enumerations/lang.model';

@Component({
  selector: 'jhi-video-update',
  templateUrl: './video-update.component.html',
})
export class VideoUpdateComponent implements OnInit {
  isSaving = false;
  video: IVideo | null = null;
  liveStateValues = Object.keys(LiveState);
  langValues = Object.keys(Lang);

  categoriesSharedCollection: ICategory[] = [];
  coachesSharedCollection: ICoach[] = [];

  editForm: VideoFormGroup = this.videoFormService.createVideoFormGroup();

  constructor(
    protected videoService: VideoService,
    protected videoFormService: VideoFormService,
    protected categoryService: CategoryService,
    protected coachService: CoachService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCategory = (o1: ICategory | null, o2: ICategory | null): boolean => this.categoryService.compareCategory(o1, o2);

  compareCoach = (o1: ICoach | null, o2: ICoach | null): boolean => this.coachService.compareCoach(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ video }) => {
      this.video = video;
      if (video) {
        this.updateForm(video);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const video = this.videoFormService.getVideo(this.editForm);
    if (video.id !== null) {
      this.subscribeToSaveResponse(this.videoService.update(video));
    } else {
      this.subscribeToSaveResponse(this.videoService.create(video));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideo>>): void {
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

  protected updateForm(video: IVideo): void {
    this.video = video;
    this.videoFormService.resetForm(this.editForm, video);

    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing<ICategory>(
      this.categoriesSharedCollection,
      video.category
    );
    this.coachesSharedCollection = this.coachService.addCoachToCollectionIfMissing<ICoach>(this.coachesSharedCollection, video.coach);
  }

  protected loadRelationshipsOptions(): void {
    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) => this.categoryService.addCategoryToCollectionIfMissing<ICategory>(categories, this.video?.category))
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));

    this.coachService
      .query()
      .pipe(map((res: HttpResponse<ICoach[]>) => res.body ?? []))
      .pipe(map((coaches: ICoach[]) => this.coachService.addCoachToCollectionIfMissing<ICoach>(coaches, this.video?.coach)))
      .subscribe((coaches: ICoach[]) => (this.coachesSharedCollection = coaches));
  }
}
