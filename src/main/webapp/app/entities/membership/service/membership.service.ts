import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMembership, NewMembership } from '../membership.model';

export type PartialUpdateMembership = Partial<IMembership> & Pick<IMembership, 'id'>;

export type EntityResponseType = HttpResponse<IMembership>;
export type EntityArrayResponseType = HttpResponse<IMembership[]>;

@Injectable({ providedIn: 'root' })
export class MembershipService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/memberships');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(membership: NewMembership): Observable<EntityResponseType> {
    return this.http.post<IMembership>(this.resourceUrl, membership, { observe: 'response' });
  }

  update(membership: IMembership): Observable<EntityResponseType> {
    return this.http.put<IMembership>(`${this.resourceUrl}/${this.getMembershipIdentifier(membership)}`, membership, {
      observe: 'response',
    });
  }

  partialUpdate(membership: PartialUpdateMembership): Observable<EntityResponseType> {
    return this.http.patch<IMembership>(`${this.resourceUrl}/${this.getMembershipIdentifier(membership)}`, membership, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMembership>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMembership[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMembershipIdentifier(membership: Pick<IMembership, 'id'>): number {
    return membership.id;
  }

  compareMembership(o1: Pick<IMembership, 'id'> | null, o2: Pick<IMembership, 'id'> | null): boolean {
    return o1 && o2 ? this.getMembershipIdentifier(o1) === this.getMembershipIdentifier(o2) : o1 === o2;
  }

  addMembershipToCollectionIfMissing<Type extends Pick<IMembership, 'id'>>(
    membershipCollection: Type[],
    ...membershipsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const memberships: Type[] = membershipsToCheck.filter(isPresent);
    if (memberships.length > 0) {
      const membershipCollectionIdentifiers = membershipCollection.map(membershipItem => this.getMembershipIdentifier(membershipItem)!);
      const membershipsToAdd = memberships.filter(membershipItem => {
        const membershipIdentifier = this.getMembershipIdentifier(membershipItem);
        if (membershipCollectionIdentifiers.includes(membershipIdentifier)) {
          return false;
        }
        membershipCollectionIdentifiers.push(membershipIdentifier);
        return true;
      });
      return [...membershipsToAdd, ...membershipCollection];
    }
    return membershipCollection;
  }
}
