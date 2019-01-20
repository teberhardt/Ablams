import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAudioLibrary } from 'app/shared/model/audio-library.model';

type EntityResponseType = HttpResponse<IAudioLibrary>;
type EntityArrayResponseType = HttpResponse<IAudioLibrary[]>;

@Injectable({ providedIn: 'root' })
export class AudioLibraryService {
    public resourceUrl = SERVER_API_URL + 'api/audio-libraries';

    constructor(protected http: HttpClient) {}

    create(audioLibrary: IAudioLibrary): Observable<EntityResponseType> {
        return this.http.post<IAudioLibrary>(this.resourceUrl, audioLibrary, { observe: 'response' });
    }

    update(audioLibrary: IAudioLibrary): Observable<EntityResponseType> {
        return this.http.put<IAudioLibrary>(this.resourceUrl, audioLibrary, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAudioLibrary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAudioLibrary[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
