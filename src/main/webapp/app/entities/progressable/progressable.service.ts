import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProgressable } from 'app/shared/model/progressable.model';

type EntityResponseType = HttpResponse<IProgressable>;
type EntityArrayResponseType = HttpResponse<IProgressable[]>;

@Injectable({ providedIn: 'root' })
export class ProgressableService {
    public resourceUrl = SERVER_API_URL + 'api/progressables';

    constructor(protected http: HttpClient) {}

    create(progressable: IProgressable): Observable<EntityResponseType> {
        return this.http.post<IProgressable>(this.resourceUrl, progressable, { observe: 'response' });
    }

    update(progressable: IProgressable): Observable<EntityResponseType> {
        return this.http.put<IProgressable>(this.resourceUrl, progressable, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProgressable>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProgressable[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
