import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AudioFile } from 'app/shared/model/audio-file.model';
import { AudioFileService } from './audio-file.service';
import { AudioFileComponent } from './audio-file.component';
import { AudioFileDetailComponent } from './audio-file-detail.component';
import { AudioFileUpdateComponent } from './audio-file-update.component';
import { AudioFileDeletePopupComponent } from './audio-file-delete-dialog.component';
import { IAudioFile } from 'app/shared/model/audio-file.model';

@Injectable({ providedIn: 'root' })
export class AudioFileResolve implements Resolve<IAudioFile> {
    constructor(private service: AudioFileService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AudioFile> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AudioFile>) => response.ok),
                map((audioFile: HttpResponse<AudioFile>) => audioFile.body)
            );
        }
        return of(new AudioFile());
    }
}

export const audioFileRoute: Routes = [
    {
        path: 'audio-file',
        component: AudioFileComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioFile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-file/:id/view',
        component: AudioFileDetailComponent,
        resolve: {
            audioFile: AudioFileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioFile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-file/new',
        component: AudioFileUpdateComponent,
        resolve: {
            audioFile: AudioFileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioFile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-file/:id/edit',
        component: AudioFileUpdateComponent,
        resolve: {
            audioFile: AudioFileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioFile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const audioFilePopupRoute: Routes = [
    {
        path: 'audio-file/:id/delete',
        component: AudioFileDeletePopupComponent,
        resolve: {
            audioFile: AudioFileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioFile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
