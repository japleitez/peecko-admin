import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILikedVideo, NewLikedVideo } from '../liked-video.model';

export type PartialUpdateLikedVideo = Partial<ILikedVideo> & Pick<ILikedVideo, 'id'>;

type RestOf<T extends ILikedVideo | NewLikedVideo> = Omit<T, 'created'> & {
  created?: string | null;
};

export type RestLikedVideo = RestOf<ILikedVideo>;

export type NewRestLikedVideo = RestOf<NewLikedVideo>;

export type PartialUpdateRestLikedVideo = RestOf<PartialUpdateLikedVideo>;

export type EntityResponseType = HttpResponse<ILikedVideo>;
export type EntityArrayResponseType = HttpResponse<ILikedVideo[]>;

@Injectable({ providedIn: 'root' })
export class LikedVideoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/liked-videos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(likedVideo: NewLikedVideo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(likedVideo);
    return this.http
      .post<RestLikedVideo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(likedVideo: ILikedVideo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(likedVideo);
    return this.http
      .put<RestLikedVideo>(`${this.resourceUrl}/${this.getLikedVideoIdentifier(likedVideo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(likedVideo: PartialUpdateLikedVideo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(likedVideo);
    return this.http
      .patch<RestLikedVideo>(`${this.resourceUrl}/${this.getLikedVideoIdentifier(likedVideo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLikedVideo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLikedVideo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLikedVideoIdentifier(likedVideo: Pick<ILikedVideo, 'id'>): number {
    return likedVideo.id;
  }

  compareLikedVideo(o1: Pick<ILikedVideo, 'id'> | null, o2: Pick<ILikedVideo, 'id'> | null): boolean {
    return o1 && o2 ? this.getLikedVideoIdentifier(o1) === this.getLikedVideoIdentifier(o2) : o1 === o2;
  }

  addLikedVideoToCollectionIfMissing<Type extends Pick<ILikedVideo, 'id'>>(
    likedVideoCollection: Type[],
    ...likedVideosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const likedVideos: Type[] = likedVideosToCheck.filter(isPresent);
    if (likedVideos.length > 0) {
      const likedVideoCollectionIdentifiers = likedVideoCollection.map(likedVideoItem => this.getLikedVideoIdentifier(likedVideoItem)!);
      const likedVideosToAdd = likedVideos.filter(likedVideoItem => {
        const likedVideoIdentifier = this.getLikedVideoIdentifier(likedVideoItem);
        if (likedVideoCollectionIdentifiers.includes(likedVideoIdentifier)) {
          return false;
        }
        likedVideoCollectionIdentifiers.push(likedVideoIdentifier);
        return true;
      });
      return [...likedVideosToAdd, ...likedVideoCollection];
    }
    return likedVideoCollection;
  }

  protected convertDateFromClient<T extends ILikedVideo | NewLikedVideo | PartialUpdateLikedVideo>(likedVideo: T): RestOf<T> {
    return {
      ...likedVideo,
      created: likedVideo.created?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLikedVideo: RestLikedVideo): ILikedVideo {
    return {
      ...restLikedVideo,
      created: restLikedVideo.created ? dayjs(restLikedVideo.created) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLikedVideo>): HttpResponse<ILikedVideo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLikedVideo[]>): HttpResponse<ILikedVideo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
