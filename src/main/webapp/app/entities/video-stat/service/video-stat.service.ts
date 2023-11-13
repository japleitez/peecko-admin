import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVideoStat, NewVideoStat } from '../video-stat.model';

export type PartialUpdateVideoStat = Partial<IVideoStat> & Pick<IVideoStat, 'id'>;

export type EntityResponseType = HttpResponse<IVideoStat>;
export type EntityArrayResponseType = HttpResponse<IVideoStat[]>;

@Injectable({ providedIn: 'root' })
export class VideoStatService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/video-stats');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(videoStat: NewVideoStat): Observable<EntityResponseType> {
    return this.http.post<IVideoStat>(this.resourceUrl, videoStat, { observe: 'response' });
  }

  update(videoStat: IVideoStat): Observable<EntityResponseType> {
    return this.http.put<IVideoStat>(`${this.resourceUrl}/${this.getVideoStatIdentifier(videoStat)}`, videoStat, { observe: 'response' });
  }

  partialUpdate(videoStat: PartialUpdateVideoStat): Observable<EntityResponseType> {
    return this.http.patch<IVideoStat>(`${this.resourceUrl}/${this.getVideoStatIdentifier(videoStat)}`, videoStat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVideoStat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVideoStat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVideoStatIdentifier(videoStat: Pick<IVideoStat, 'id'>): number {
    return videoStat.id;
  }

  compareVideoStat(o1: Pick<IVideoStat, 'id'> | null, o2: Pick<IVideoStat, 'id'> | null): boolean {
    return o1 && o2 ? this.getVideoStatIdentifier(o1) === this.getVideoStatIdentifier(o2) : o1 === o2;
  }

  addVideoStatToCollectionIfMissing<Type extends Pick<IVideoStat, 'id'>>(
    videoStatCollection: Type[],
    ...videoStatsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const videoStats: Type[] = videoStatsToCheck.filter(isPresent);
    if (videoStats.length > 0) {
      const videoStatCollectionIdentifiers = videoStatCollection.map(videoStatItem => this.getVideoStatIdentifier(videoStatItem)!);
      const videoStatsToAdd = videoStats.filter(videoStatItem => {
        const videoStatIdentifier = this.getVideoStatIdentifier(videoStatItem);
        if (videoStatCollectionIdentifiers.includes(videoStatIdentifier)) {
          return false;
        }
        videoStatCollectionIdentifiers.push(videoStatIdentifier);
        return true;
      });
      return [...videoStatsToAdd, ...videoStatCollection];
    }
    return videoStatCollection;
  }
}
