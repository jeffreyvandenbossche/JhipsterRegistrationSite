import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPartyPart } from 'app/shared/model/party-part.model';

type EntityResponseType = HttpResponse<IPartyPart>;
type EntityArrayResponseType = HttpResponse<IPartyPart[]>;

@Injectable({ providedIn: 'root' })
export class PartyPartService {
    public resourceUrl = SERVER_API_URL + 'api/party-parts';

    constructor(private http: HttpClient) {}

    create(partyPart: IPartyPart): Observable<EntityResponseType> {
        return this.http.post<IPartyPart>(this.resourceUrl, partyPart, { observe: 'response' });
    }

    update(partyPart: IPartyPart): Observable<EntityResponseType> {
        return this.http.put<IPartyPart>(this.resourceUrl, partyPart, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPartyPart>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPartyPart[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
