import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISecuredRequest, NewSecuredRequest } from '../secured-request.model';

export type PartialUpdateSecuredRequest = Partial<ISecuredRequest> & Pick<ISecuredRequest, 'id'>;

type RestOf<T extends ISecuredRequest | NewSecuredRequest> = Omit<T, 'created' | 'expires'> & {
  created?: string | null;
  expires?: string | null;
};

export type RestSecuredRequest = RestOf<ISecuredRequest>;

export type NewRestSecuredRequest = RestOf<NewSecuredRequest>;

export type PartialUpdateRestSecuredRequest = RestOf<PartialUpdateSecuredRequest>;

export type EntityResponseType = HttpResponse<ISecuredRequest>;
export type EntityArrayResponseType = HttpResponse<ISecuredRequest[]>;

@Injectable({ providedIn: 'root' })
export class SecuredRequestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/secured-requests');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(securedRequest: NewSecuredRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securedRequest);
    return this.http
      .post<RestSecuredRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(securedRequest: ISecuredRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securedRequest);
    return this.http
      .put<RestSecuredRequest>(`${this.resourceUrl}/${this.getSecuredRequestIdentifier(securedRequest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(securedRequest: PartialUpdateSecuredRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securedRequest);
    return this.http
      .patch<RestSecuredRequest>(`${this.resourceUrl}/${this.getSecuredRequestIdentifier(securedRequest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSecuredRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSecuredRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSecuredRequestIdentifier(securedRequest: Pick<ISecuredRequest, 'id'>): number {
    return securedRequest.id;
  }

  compareSecuredRequest(o1: Pick<ISecuredRequest, 'id'> | null, o2: Pick<ISecuredRequest, 'id'> | null): boolean {
    return o1 && o2 ? this.getSecuredRequestIdentifier(o1) === this.getSecuredRequestIdentifier(o2) : o1 === o2;
  }

  addSecuredRequestToCollectionIfMissing<Type extends Pick<ISecuredRequest, 'id'>>(
    securedRequestCollection: Type[],
    ...securedRequestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const securedRequests: Type[] = securedRequestsToCheck.filter(isPresent);
    if (securedRequests.length > 0) {
      const securedRequestCollectionIdentifiers = securedRequestCollection.map(
        securedRequestItem => this.getSecuredRequestIdentifier(securedRequestItem)!,
      );
      const securedRequestsToAdd = securedRequests.filter(securedRequestItem => {
        const securedRequestIdentifier = this.getSecuredRequestIdentifier(securedRequestItem);
        if (securedRequestCollectionIdentifiers.includes(securedRequestIdentifier)) {
          return false;
        }
        securedRequestCollectionIdentifiers.push(securedRequestIdentifier);
        return true;
      });
      return [...securedRequestsToAdd, ...securedRequestCollection];
    }
    return securedRequestCollection;
  }

  protected convertDateFromClient<T extends ISecuredRequest | NewSecuredRequest | PartialUpdateSecuredRequest>(
    securedRequest: T,
  ): RestOf<T> {
    return {
      ...securedRequest,
      created: securedRequest.created?.toJSON() ?? null,
      expires: securedRequest.expires?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSecuredRequest: RestSecuredRequest): ISecuredRequest {
    return {
      ...restSecuredRequest,
      created: restSecuredRequest.created ? dayjs(restSecuredRequest.created) : undefined,
      expires: restSecuredRequest.expires ? dayjs(restSecuredRequest.expires) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSecuredRequest>): HttpResponse<ISecuredRequest> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSecuredRequest[]>): HttpResponse<ISecuredRequest[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
