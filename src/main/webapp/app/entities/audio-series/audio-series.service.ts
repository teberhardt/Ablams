import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAudioSeries } from 'app/shared/model/audio-series.model';

type EntityResponseType = HttpResponse<IAudioSeries>;
type EntityArrayResponseType = HttpResponse<IAudioSeries[]>;

@Injectable({ providedIn: 'root' })
export class AudioSeriesService {
    public resourceUrl = SERVER_API_URL + 'api/audio-series';

    constructor(protected http: HttpClient) {}

    create(audioSeries: IAudioSeries): Observable<EntityResponseType> {
        return this.http.post<IAudioSeries>(this.resourceUrl, audioSeries, { observe: 'response' });
    }

    update(audioSeries: IAudioSeries): Observable<EntityResponseType> {
        return this.http.put<IAudioSeries>(this.resourceUrl, audioSeries, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAudioSeries>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAudioSeries[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
