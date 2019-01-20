import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AudioBook } from 'app/shared/model/audio-book.model';
import { AudioBookService } from './audio-book.service';
import { AudioBookComponent } from './audio-book.component';
import { AudioBookDetailComponent } from './audio-book-detail.component';
import { AudioBookUpdateComponent } from './audio-book-update.component';
import { AudioBookDeletePopupComponent } from './audio-book-delete-dialog.component';
import { IAudioBook } from 'app/shared/model/audio-book.model';

@Injectable({ providedIn: 'root' })
export class AudioBookResolve implements Resolve<IAudioBook> {
    constructor(private service: AudioBookService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AudioBook> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AudioBook>) => response.ok),
                map((audioBook: HttpResponse<AudioBook>) => audioBook.body)
            );
        }
        return of(new AudioBook());
    }
}

export const audioBookRoute: Routes = [
    {
        path: 'audio-book',
        component: AudioBookComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioBook.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-book/:id/view',
        component: AudioBookDetailComponent,
        resolve: {
            audioBook: AudioBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioBook.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-book/new',
        component: AudioBookUpdateComponent,
        resolve: {
            audioBook: AudioBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioBook.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-book/:id/edit',
        component: AudioBookUpdateComponent,
        resolve: {
            audioBook: AudioBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioBook.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const audioBookPopupRoute: Routes = [
    {
        path: 'audio-book/:id/delete',
        component: AudioBookDeletePopupComponent,
        resolve: {
            audioBook: AudioBookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioBook.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
