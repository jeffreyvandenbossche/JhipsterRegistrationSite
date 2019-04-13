import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccesscode } from 'app/shared/model/accesscode.model';

type EntityResponseType = HttpResponse<IAccesscode>;
type EntityArrayResponseType = HttpResponse<IAccesscode[]>;

@Injectable({ providedIn: 'root' })
export class AccesscodeService {
    public resourceUrl = SERVER_API_URL + 'api/accesscodes';

    constructor(private http: HttpClient) {}

    create(accesscode: IAccesscode): Observable<EntityResponseType> {
        return this.http.post<IAccesscode>(this.resourceUrl, accesscode, { observe: 'response' });
    }

    update(accesscode: IAccesscode): Observable<EntityResponseType> {
        return this.http.put<IAccesscode>(this.resourceUrl, accesscode, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAccesscode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAccesscode[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
