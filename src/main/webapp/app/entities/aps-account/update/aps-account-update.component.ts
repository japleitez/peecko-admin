import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { Language } from 'app/entities/enumerations/language.model';
import { IApsAccount } from '../aps-account.model';
import { ApsAccountService } from '../service/aps-account.service';
import { ApsAccountFormService, ApsAccountFormGroup } from './aps-account-form.service';

@Component({
  standalone: true,
  selector: 'jhi-aps-account-update',
  templateUrl: './aps-account-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ApsAccountUpdateComponent implements OnInit {
  isSaving = false;
  apsAccount: IApsAccount | null = null;
  yesNoValues = Object.keys(YesNo);
  languageValues = Object.keys(Language);

  editForm: ApsAccountFormGroup = this.apsAccountFormService.createApsAccountFormGroup();

  constructor(
    protected apsAccountService: ApsAccountService,
    protected apsAccountFormService: ApsAccountFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apsAccount }) => {
      this.apsAccount = apsAccount;
      if (apsAccount) {
        this.updateForm(apsAccount);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apsAccount = this.apsAccountFormService.getApsAccount(this.editForm);
    if (apsAccount.id !== null) {
      this.subscribeToSaveResponse(this.apsAccountService.update(apsAccount));
    } else {
      this.subscribeToSaveResponse(this.apsAccountService.create(apsAccount));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApsAccount>>): void {
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

  protected updateForm(apsAccount: IApsAccount): void {
    this.apsAccount = apsAccount;
    this.apsAccountFormService.resetForm(this.editForm, apsAccount);
  }
}
