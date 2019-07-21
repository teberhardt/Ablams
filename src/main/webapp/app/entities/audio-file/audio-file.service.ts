import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAudioFile } from 'app/shared/model/audio-file.model';

type EntityResponseType = HttpResponse<IAudioFile>;
type EntityArrayResponseType = HttpResponse<IAudioFile[]>;

@Injectable({ providedIn: 'root' })
export class AudioFileService {
    public resourceUrl = SERVER_API_URL + 'api/audio-files';

    constructor(protected http: HttpClient) {}

    create(audioFile: IAudioFile): Observable<EntityResponseType> {
        return this.http.post<IAudioFile>(this.resourceUrl, audioFile, { observe: 'response' });
    }

    update(audioFile: IAudioFile): Observable<EntityResponseType> {
        return this.http.put<IAudioFile>(this.resourceUrl, audioFile, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAudioFile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAudioFile[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
