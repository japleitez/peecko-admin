import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IPlaylist } from 'app/entities/playlist/playlist.model';
import { PlaylistService } from 'app/entities/playlist/service/playlist.service';
import { VideoItemService } from '../service/video-item.service';
import { IVideoItem } from '../video-item.model';
import { VideoItemFormService } from './video-item-form.service';

import { VideoItemUpdateComponent } from './video-item-update.component';

describe('VideoItem Management Update Component', () => {
  let comp: VideoItemUpdateComponent;
  let fixture: ComponentFixture<VideoItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let videoItemFormService: VideoItemFormService;
  let videoItemService: VideoItemService;
  let playlistService: PlaylistService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), VideoItemUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(VideoItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VideoItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    videoItemFormService = TestBed.inject(VideoItemFormService);
    videoItemService = TestBed.inject(VideoItemService);
    playlistService = TestBed.inject(PlaylistService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Playlist query and add missing value', () => {
      const videoItem: IVideoItem = { id: 456 };
      const playlist: IPlaylist = { id: 17392 };
      videoItem.playlist = playlist;

      const playlistCollection: IPlaylist[] = [{ id: 22671 }];
      jest.spyOn(playlistService, 'query').mockReturnValue(of(new HttpResponse({ body: playlistCollection })));
      const additionalPlaylists = [playlist];
      const expectedCollection: IPlaylist[] = [...additionalPlaylists, ...playlistCollection];
      jest.spyOn(playlistService, 'addPlaylistToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ videoItem });
      comp.ngOnInit();

      expect(playlistService.query).toHaveBeenCalled();
      expect(playlistService.addPlaylistToCollectionIfMissing).toHaveBeenCalledWith(
        playlistCollection,
        ...additionalPlaylists.map(expect.objectContaining),
      );
      expect(comp.playlistsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const videoItem: IVideoItem = { id: 456 };
      const playlist: IPlaylist = { id: 5389 };
      videoItem.playlist = playlist;

      activatedRoute.data = of({ videoItem });
      comp.ngOnInit();

      expect(comp.playlistsSharedCollection).toContain(playlist);
      expect(comp.videoItem).toEqual(videoItem);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideoItem>>();
      const videoItem = { id: 123 };
      jest.spyOn(videoItemFormService, 'getVideoItem').mockReturnValue(videoItem);
      jest.spyOn(videoItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ videoItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: videoItem }));
      saveSubject.complete();

      // THEN
      expect(videoItemFormService.getVideoItem).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(videoItemService.update).toHaveBeenCalledWith(expect.objectContaining(videoItem));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideoItem>>();
      const videoItem = { id: 123 };
      jest.spyOn(videoItemFormService, 'getVideoItem').mockReturnValue({ id: null });
      jest.spyOn(videoItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ videoItem: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: videoItem }));
      saveSubject.complete();

      // THEN
      expect(videoItemFormService.getVideoItem).toHaveBeenCalled();
      expect(videoItemService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVideoItem>>();
      const videoItem = { id: 123 };
      jest.spyOn(videoItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ videoItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(videoItemService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePlaylist', () => {
      it('Should forward to playlistService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(playlistService, 'comparePlaylist');
        comp.comparePlaylist(entity, entity2);
        expect(playlistService.comparePlaylist).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
