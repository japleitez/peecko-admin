import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MembershipFormService, MembershipFormGroup } from './membership-form.service';
import { IMembership } from '../membership.model';
import { MembershipService } from '../service/membership.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

@Component({
  selector: 'jhi-membership-update',
  templateUrl: './membership-update.component.html',
})
export class MembershipUpdateComponent implements OnInit {
  isSaving = false;
  membership: IMembership | null = null;

  companiesSharedCollection: ICompany[] = [];

  editForm: MembershipFormGroup = this.membershipFormService.createMembershipFormGroup();

  constructor(
    protected membershipService: MembershipService,
    protected membershipFormService: MembershipFormService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membership }) => {
      this.membership = membership;
      if (membership) {
        this.updateForm(membership);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const membership = this.membershipFormService.getMembership(this.editForm);
    if (membership.id !== null) {
      this.subscribeToSaveResponse(this.membershipService.update(membership));
    } else {
      this.subscribeToSaveResponse(this.membershipService.create(membership));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembership>>): void {
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

  protected updateForm(membership: IMembership): void {
    this.membership = membership;
    this.membershipFormService.resetForm(this.editForm, membership);

    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      membership.company
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.membership?.company))
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));
  }
}
