import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApsAccount, NewApsAccount } from '../aps-account.model';

export type PartialUpdateApsAccount = Partial<IApsAccount> & Pick<IApsAccount, 'id'>;

type RestOf<T extends IApsAccount | NewApsAccount> = Omit<T, 'created' | 'updated'> & {
  created?: string | null;
  updated?: string | null;
};

export type RestApsAccount = RestOf<IApsAccount>;

export type NewRestApsAccount = RestOf<NewApsAccount>;

export type PartialUpdateRestApsAccount = RestOf<PartialUpdateApsAccount>;

export type EntityResponseType = HttpResponse<IApsAccount>;
export type EntityArrayResponseType = HttpResponse<IApsAccount[]>;

@Injectable({ providedIn: 'root' })
export class ApsAccountService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/aps-accounts');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(apsAccount: NewApsAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(apsAccount);
    return this.http
      .post<RestApsAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(apsAccount: IApsAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(apsAccount);
    return this.http
      .put<RestApsAccount>(`${this.resourceUrl}/${this.getApsAccountIdentifier(apsAccount)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(apsAccount: PartialUpdateApsAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(apsAccount);
    return this.http
      .patch<RestApsAccount>(`${this.resourceUrl}/${this.getApsAccountIdentifier(apsAccount)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestApsAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestApsAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getApsAccountIdentifier(apsAccount: Pick<IApsAccount, 'id'>): number {
    return apsAccount.id;
  }

  compareApsAccount(o1: Pick<IApsAccount, 'id'> | null, o2: Pick<IApsAccount, 'id'> | null): boolean {
    return o1 && o2 ? this.getApsAccountIdentifier(o1) === this.getApsAccountIdentifier(o2) : o1 === o2;
  }

  addApsAccountToCollectionIfMissing<Type extends Pick<IApsAccount, 'id'>>(
    apsAccountCollection: Type[],
    ...apsAccountsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const apsAccounts: Type[] = apsAccountsToCheck.filter(isPresent);
    if (apsAccounts.length > 0) {
      const apsAccountCollectionIdentifiers = apsAccountCollection.map(apsAccountItem => this.getApsAccountIdentifier(apsAccountItem)!);
      const apsAccountsToAdd = apsAccounts.filter(apsAccountItem => {
        const apsAccountIdentifier = this.getApsAccountIdentifier(apsAccountItem);
        if (apsAccountCollectionIdentifiers.includes(apsAccountIdentifier)) {
          return false;
        }
        apsAccountCollectionIdentifiers.push(apsAccountIdentifier);
        return true;
      });
      return [...apsAccountsToAdd, ...apsAccountCollection];
    }
    return apsAccountCollection;
  }

  protected convertDateFromClient<T extends IApsAccount | NewApsAccount | PartialUpdateApsAccount>(apsAccount: T): RestOf<T> {
    return {
      ...apsAccount,
      created: apsAccount.created?.toJSON() ?? null,
      updated: apsAccount.updated?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restApsAccount: RestApsAccount): IApsAccount {
    return {
      ...restApsAccount,
      created: restApsAccount.created ? dayjs(restApsAccount.created) : undefined,
      updated: restApsAccount.updated ? dayjs(restApsAccount.updated) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestApsAccount>): HttpResponse<IApsAccount> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestApsAccount[]>): HttpResponse<IApsAccount[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
