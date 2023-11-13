import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IApsAccount } from 'app/entities/aps-account/aps-account.model';
import { ApsAccountService } from 'app/entities/aps-account/service/aps-account.service';
import { PlaylistService } from '../service/playlist.service';
import { IPlaylist } from '../playlist.model';
import { PlaylistFormService } from './playlist-form.service';

import { PlaylistUpdateComponent } from './playlist-update.component';

describe('Playlist Management Update Component', () => {
  let comp: PlaylistUpdateComponent;
  let fixture: ComponentFixture<PlaylistUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let playlistFormService: PlaylistFormService;
  let playlistService: PlaylistService;
  let apsAccountService: ApsAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PlaylistUpdateComponent],
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
      .overrideTemplate(PlaylistUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlaylistUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    playlistFormService = TestBed.inject(PlaylistFormService);
    playlistService = TestBed.inject(PlaylistService);
    apsAccountService = TestBed.inject(ApsAccountService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ApsAccount query and add missing value', () => {
      const playlist: IPlaylist = { id: 456 };
      const apsAccount: IApsAccount = { id: 7572 };
      playlist.apsAccount = apsAccount;

      const apsAccountCollection: IApsAccount[] = [{ id: 8167 }];
      jest.spyOn(apsAccountService, 'query').mockReturnValue(of(new HttpResponse({ body: apsAccountCollection })));
      const additionalApsAccounts = [apsAccount];
      const expectedCollection: IApsAccount[] = [...additionalApsAccounts, ...apsAccountCollection];
      jest.spyOn(apsAccountService, 'addApsAccountToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ playlist });
      comp.ngOnInit();

      expect(apsAccountService.query).toHaveBeenCalled();
      expect(apsAccountService.addApsAccountToCollectionIfMissing).toHaveBeenCalledWith(
        apsAccountCollection,
        ...additionalApsAccounts.map(expect.objectContaining),
      );
      expect(comp.apsAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const playlist: IPlaylist = { id: 456 };
      const apsAccount: IApsAccount = { id: 20384 };
      playlist.apsAccount = apsAccount;

      activatedRoute.data = of({ playlist });
      comp.ngOnInit();

      expect(comp.apsAccountsSharedCollection).toContain(apsAccount);
      expect(comp.playlist).toEqual(playlist);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlaylist>>();
      const playlist = { id: 123 };
      jest.spyOn(playlistFormService, 'getPlaylist').mockReturnValue(playlist);
      jest.spyOn(playlistService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ playlist });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: playlist }));
      saveSubject.complete();

      // THEN
      expect(playlistFormService.getPlaylist).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(playlistService.update).toHaveBeenCalledWith(expect.objectContaining(playlist));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlaylist>>();
      const playlist = { id: 123 };
      jest.spyOn(playlistFormService, 'getPlaylist').mockReturnValue({ id: null });
      jest.spyOn(playlistService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ playlist: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: playlist }));
      saveSubject.complete();

      // THEN
      expect(playlistFormService.getPlaylist).toHaveBeenCalled();
      expect(playlistService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlaylist>>();
      const playlist = { id: 123 };
      jest.spyOn(playlistService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ playlist });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(playlistService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareApsAccount', () => {
      it('Should forward to apsAccountService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(apsAccountService, 'compareApsAccount');
        comp.compareApsAccount(entity, entity2);
        expect(apsAccountService.compareApsAccount).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
