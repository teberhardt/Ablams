import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAudioBook } from 'app/shared/model/audio-book.model';

type EntityResponseType = HttpResponse<IAudioBook>;
type EntityArrayResponseType = HttpResponse<IAudioBook[]>;

@Injectable({ providedIn: 'root' })
export class AudioBookService {
    public resourceUrl = SERVER_API_URL + 'api/audio-books';

    constructor(protected http: HttpClient) {}

    create(audioBook: IAudioBook): Observable<EntityResponseType> {
        return this.http.post<IAudioBook>(this.resourceUrl, audioBook, { observe: 'response' });
    }

    update(audioBook: IAudioBook): Observable<EntityResponseType> {
        return this.http.put<IAudioBook>(this.resourceUrl, audioBook, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAudioBook>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAudioBook[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
