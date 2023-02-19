import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PlanFormService, PlanFormGroup } from './plan-form.service';
import { IPlan } from '../plan.model';
import { PlanService } from '../service/plan.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-plan-update',
  templateUrl: './plan-update.component.html',
})
export class PlanUpdateComponent implements OnInit {
  isSaving = false;
  plan: IPlan | null = null;
  yesNoValues = Object.keys(YesNo);

  companiesSharedCollection: ICompany[] = [];

  editForm: PlanFormGroup = this.planFormService.createPlanFormGroup();

  constructor(
    protected planService: PlanService,
    protected planFormService: PlanFormService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plan }) => {
      this.plan = plan;
      if (plan) {
        this.updateForm(plan);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plan = this.planFormService.getPlan(this.editForm);
    if (plan.id !== null) {
      this.subscribeToSaveResponse(this.planService.update(plan));
    } else {
      this.subscribeToSaveResponse(this.planService.create(plan));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlan>>): void {
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

  protected updateForm(plan: IPlan): void {
    this.plan = plan;
    this.planFormService.resetForm(this.editForm, plan);

    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      plan.company
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.plan?.company)))
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));
  }
}
