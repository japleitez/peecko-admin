import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VideoStatDetailComponent } from './video-stat-detail.component';

describe('VideoStat Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VideoStatDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: VideoStatDetailComponent,
              resolve: { videoStat: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(VideoStatDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load videoStat on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VideoStatDetailComponent);

      // THEN
      expect(instance.videoStat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
