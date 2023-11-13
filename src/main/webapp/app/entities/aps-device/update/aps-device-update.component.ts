import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IApsAccount } from 'app/entities/aps-account/aps-account.model';
import { ApsAccountService } from 'app/entities/aps-account/service/aps-account.service';
import { IApsDevice } from '../aps-device.model';
import { ApsDeviceService } from '../service/aps-device.service';
import { ApsDeviceFormService, ApsDeviceFormGroup } from './aps-device-form.service';

@Component({
  standalone: true,
  selector: 'jhi-aps-device-update',
  templateUrl: './aps-device-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ApsDeviceUpdateComponent implements OnInit {
  isSaving = false;
  apsDevice: IApsDevice | null = null;

  apsAccountsSharedCollection: IApsAccount[] = [];

  editForm: ApsDeviceFormGroup = this.apsDeviceFormService.createApsDeviceFormGroup();

  constructor(
    protected apsDeviceService: ApsDeviceService,
    protected apsDeviceFormService: ApsDeviceFormService,
    protected apsAccountService: ApsAccountService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareApsAccount = (o1: IApsAccount | null, o2: IApsAccount | null): boolean => this.apsAccountService.compareApsAccount(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apsDevice }) => {
      this.apsDevice = apsDevice;
      if (apsDevice) {
        this.updateForm(apsDevice);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apsDevice = this.apsDeviceFormService.getApsDevice(this.editForm);
    if (apsDevice.id !== null) {
      this.subscribeToSaveResponse(this.apsDeviceService.update(apsDevice));
    } else {
      this.subscribeToSaveResponse(this.apsDeviceService.create(apsDevice));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApsDevice>>): void {
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

  protected updateForm(apsDevice: IApsDevice): void {
    this.apsDevice = apsDevice;
    this.apsDeviceFormService.resetForm(this.editForm, apsDevice);

    this.apsAccountsSharedCollection = this.apsAccountService.addApsAccountToCollectionIfMissing<IApsAccount>(
      this.apsAccountsSharedCollection,
      apsDevice.apsAccount,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.apsAccountService
      .query()
      .pipe(map((res: HttpResponse<IApsAccount[]>) => res.body ?? []))
      .pipe(
        map((apsAccounts: IApsAccount[]) =>
          this.apsAccountService.addApsAccountToCollectionIfMissing<IApsAccount>(apsAccounts, this.apsDevice?.apsAccount),
        ),
      )
      .subscribe((apsAccounts: IApsAccount[]) => (this.apsAccountsSharedCollection = apsAccounts));
  }
}
