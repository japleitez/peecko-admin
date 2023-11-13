import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISecuredRequest } from '../secured-request.model';
import { SecuredRequestService } from '../service/secured-request.service';
import { SecuredRequestFormService, SecuredRequestFormGroup } from './secured-request-form.service';

@Component({
  standalone: true,
  selector: 'jhi-secured-request-update',
  templateUrl: './secured-request-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SecuredRequestUpdateComponent implements OnInit {
  isSaving = false;
  securedRequest: ISecuredRequest | null = null;

  editForm: SecuredRequestFormGroup = this.securedRequestFormService.createSecuredRequestFormGroup();

  constructor(
    protected securedRequestService: SecuredRequestService,
    protected securedRequestFormService: SecuredRequestFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securedRequest }) => {
      this.securedRequest = securedRequest;
      if (securedRequest) {
        this.updateForm(securedRequest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securedRequest = this.securedRequestFormService.getSecuredRequest(this.editForm);
    if (securedRequest.id !== null) {
      this.subscribeToSaveResponse(this.securedRequestService.update(securedRequest));
    } else {
      this.subscribeToSaveResponse(this.securedRequestService.create(securedRequest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecuredRequest>>): void {
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

  protected updateForm(securedRequest: ISecuredRequest): void {
    this.securedRequest = securedRequest;
    this.securedRequestFormService.resetForm(this.editForm, securedRequest);
  }
}
