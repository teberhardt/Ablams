import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AudioLibrary } from 'app/shared/model/audio-library.model';
import { AudioLibraryService } from './audio-library.service';
import { AudioLibraryComponent } from './audio-library.component';
import { AudioLibraryDetailComponent } from './audio-library-detail.component';
import { AudioLibraryUpdateComponent } from './audio-library-update.component';
import { AudioLibraryDeletePopupComponent } from './audio-library-delete-dialog.component';
import { IAudioLibrary } from 'app/shared/model/audio-library.model';

@Injectable({ providedIn: 'root' })
export class AudioLibraryResolve implements Resolve<IAudioLibrary> {
    constructor(private service: AudioLibraryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AudioLibrary> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AudioLibrary>) => response.ok),
                map((audioLibrary: HttpResponse<AudioLibrary>) => audioLibrary.body)
            );
        }
        return of(new AudioLibrary());
    }
}

export const audioLibraryRoute: Routes = [
    {
        path: 'audio-library',
        component: AudioLibraryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioLibrary.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-library/:id/view',
        component: AudioLibraryDetailComponent,
        resolve: {
            audioLibrary: AudioLibraryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioLibrary.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-library/new',
        component: AudioLibraryUpdateComponent,
        resolve: {
            audioLibrary: AudioLibraryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioLibrary.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-library/:id/edit',
        component: AudioLibraryUpdateComponent,
        resolve: {
            audioLibrary: AudioLibraryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioLibrary.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const audioLibraryPopupRoute: Routes = [
    {
        path: 'audio-library/:id/delete',
        component: AudioLibraryDeletePopupComponent,
        resolve: {
            audioLibrary: AudioLibraryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioLibrary.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
