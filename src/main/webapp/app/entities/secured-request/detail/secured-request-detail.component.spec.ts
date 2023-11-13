import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SecuredRequestDetailComponent } from './secured-request-detail.component';

describe('SecuredRequest Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SecuredRequestDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SecuredRequestDetailComponent,
              resolve: { securedRequest: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SecuredRequestDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load securedRequest on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SecuredRequestDetailComponent);

      // THEN
      expect(instance.securedRequest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
