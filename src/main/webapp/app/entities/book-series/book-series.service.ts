import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBookSeries } from 'app/shared/model/book-series.model';

type EntityResponseType = HttpResponse<IBookSeries>;
type EntityArrayResponseType = HttpResponse<IBookSeries[]>;

@Injectable({ providedIn: 'root' })
export class BookSeriesService {
    public resourceUrl = SERVER_API_URL + 'api/book-series';

    constructor(protected http: HttpClient) {}

    create(bookSeries: IBookSeries): Observable<EntityResponseType> {
        return this.http.post<IBookSeries>(this.resourceUrl, bookSeries, { observe: 'response' });
    }

    update(bookSeries: IBookSeries): Observable<EntityResponseType> {
        return this.http.put<IBookSeries>(this.resourceUrl, bookSeries, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBookSeries>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBookSeries[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
