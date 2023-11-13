import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CodeTranslationDetailComponent } from './code-translation-detail.component';

describe('CodeTranslation Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CodeTranslationDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CodeTranslationDetailComponent,
              resolve: { codeTranslation: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CodeTranslationDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load codeTranslation on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CodeTranslationDetailComponent);

      // THEN
      expect(instance.codeTranslation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
