import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CoachFormService, CoachFormGroup } from './coach-form.service';
import { ICoach } from '../coach.model';
import { CoachService } from '../service/coach.service';

@Component({
  selector: 'jhi-coach-update',
  templateUrl: './coach-update.component.html',
})
export class CoachUpdateComponent implements OnInit {
  isSaving = false;
  coach: ICoach | null = null;

  editForm: CoachFormGroup = this.coachFormService.createCoachFormGroup();

  constructor(
    protected coachService: CoachService,
    protected coachFormService: CoachFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ coach }) => {
      this.coach = coach;
      if (coach) {
        this.updateForm(coach);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const coach = this.coachFormService.getCoach(this.editForm);
    if (coach.id !== null) {
      this.subscribeToSaveResponse(this.coachService.update(coach));
    } else {
      this.subscribeToSaveResponse(this.coachService.create(coach));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoach>>): void {
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

  protected updateForm(coach: ICoach): void {
    this.coach = coach;
    this.coachFormService.resetForm(this.editForm, coach);
  }
}
