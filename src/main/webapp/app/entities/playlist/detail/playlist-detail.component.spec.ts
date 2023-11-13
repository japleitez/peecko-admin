import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PlaylistDetailComponent } from './playlist-detail.component';

describe('Playlist Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlaylistDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PlaylistDetailComponent,
              resolve: { playlist: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PlaylistDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load playlist on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PlaylistDetailComponent);

      // THEN
      expect(instance.playlist).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
