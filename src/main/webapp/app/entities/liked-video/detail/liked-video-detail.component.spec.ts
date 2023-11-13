import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LikedVideoDetailComponent } from './liked-video-detail.component';

describe('LikedVideo Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LikedVideoDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: LikedVideoDetailComponent,
              resolve: { likedVideo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(LikedVideoDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load likedVideo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LikedVideoDetailComponent);

      // THEN
      expect(instance.likedVideo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
