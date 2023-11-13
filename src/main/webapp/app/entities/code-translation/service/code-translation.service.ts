import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICodeTranslation, NewCodeTranslation } from '../code-translation.model';

export type PartialUpdateCodeTranslation = Partial<ICodeTranslation> & Pick<ICodeTranslation, 'id'>;

export type EntityResponseType = HttpResponse<ICodeTranslation>;
export type EntityArrayResponseType = HttpResponse<ICodeTranslation[]>;

@Injectable({ providedIn: 'root' })
export class CodeTranslationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/code-translations');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(codeTranslation: NewCodeTranslation): Observable<EntityResponseType> {
    return this.http.post<ICodeTranslation>(this.resourceUrl, codeTranslation, { observe: 'response' });
  }

  update(codeTranslation: ICodeTranslation): Observable<EntityResponseType> {
    return this.http.put<ICodeTranslation>(`${this.resourceUrl}/${this.getCodeTranslationIdentifier(codeTranslation)}`, codeTranslation, {
      observe: 'response',
    });
  }

  partialUpdate(codeTranslation: PartialUpdateCodeTranslation): Observable<EntityResponseType> {
    return this.http.patch<ICodeTranslation>(`${this.resourceUrl}/${this.getCodeTranslationIdentifier(codeTranslation)}`, codeTranslation, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICodeTranslation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICodeTranslation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCodeTranslationIdentifier(codeTranslation: Pick<ICodeTranslation, 'id'>): number {
    return codeTranslation.id;
  }

  compareCodeTranslation(o1: Pick<ICodeTranslation, 'id'> | null, o2: Pick<ICodeTranslation, 'id'> | null): boolean {
    return o1 && o2 ? this.getCodeTranslationIdentifier(o1) === this.getCodeTranslationIdentifier(o2) : o1 === o2;
  }

  addCodeTranslationToCollectionIfMissing<Type extends Pick<ICodeTranslation, 'id'>>(
    codeTranslationCollection: Type[],
    ...codeTranslationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const codeTranslations: Type[] = codeTranslationsToCheck.filter(isPresent);
    if (codeTranslations.length > 0) {
      const codeTranslationCollectionIdentifiers = codeTranslationCollection.map(
        codeTranslationItem => this.getCodeTranslationIdentifier(codeTranslationItem)!,
      );
      const codeTranslationsToAdd = codeTranslations.filter(codeTranslationItem => {
        const codeTranslationIdentifier = this.getCodeTranslationIdentifier(codeTranslationItem);
        if (codeTranslationCollectionIdentifiers.includes(codeTranslationIdentifier)) {
          return false;
        }
        codeTranslationCollectionIdentifiers.push(codeTranslationIdentifier);
        return true;
      });
      return [...codeTranslationsToAdd, ...codeTranslationCollection];
    }
    return codeTranslationCollection;
  }
}
