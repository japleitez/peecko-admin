import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { Language } from 'app/entities/enumerations/language.model';
import { ICodeTranslation } from '../code-translation.model';
import { CodeTranslationService } from '../service/code-translation.service';
import { CodeTranslationFormService, CodeTranslationFormGroup } from './code-translation-form.service';

@Component({
  standalone: true,
  selector: 'jhi-code-translation-update',
  templateUrl: './code-translation-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CodeTranslationUpdateComponent implements OnInit {
  isSaving = false;
  codeTranslation: ICodeTranslation | null = null;
  languageValues = Object.keys(Language);

  editForm: CodeTranslationFormGroup = this.codeTranslationFormService.createCodeTranslationFormGroup();

  constructor(
    protected codeTranslationService: CodeTranslationService,
    protected codeTranslationFormService: CodeTranslationFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ codeTranslation }) => {
      this.codeTranslation = codeTranslation;
      if (codeTranslation) {
        this.updateForm(codeTranslation);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const codeTranslation = this.codeTranslationFormService.getCodeTranslation(this.editForm);
    if (codeTranslation.id !== null) {
      this.subscribeToSaveResponse(this.codeTranslationService.update(codeTranslation));
    } else {
      this.subscribeToSaveResponse(this.codeTranslationService.create(codeTranslation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICodeTranslation>>): void {
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

  protected updateForm(codeTranslation: ICodeTranslation): void {
    this.codeTranslation = codeTranslation;
    this.codeTranslationFormService.resetForm(this.editForm, codeTranslation);
  }
}
