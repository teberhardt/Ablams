import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AudioSeries } from 'app/shared/model/audio-series.model';
import { AudioSeriesService } from './audio-series.service';
import { AudioSeriesComponent } from './audio-series.component';
import { AudioSeriesDetailComponent } from './audio-series-detail.component';
import { AudioSeriesUpdateComponent } from './audio-series-update.component';
import { AudioSeriesDeletePopupComponent } from './audio-series-delete-dialog.component';
import { IAudioSeries } from 'app/shared/model/audio-series.model';

@Injectable({ providedIn: 'root' })
export class AudioSeriesResolve implements Resolve<IAudioSeries> {
    constructor(private service: AudioSeriesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AudioSeries> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AudioSeries>) => response.ok),
                map((audioSeries: HttpResponse<AudioSeries>) => audioSeries.body)
            );
        }
        return of(new AudioSeries());
    }
}

export const audioSeriesRoute: Routes = [
    {
        path: 'audio-series',
        component: AudioSeriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-series/:id/view',
        component: AudioSeriesDetailComponent,
        resolve: {
            audioSeries: AudioSeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-series/new',
        component: AudioSeriesUpdateComponent,
        resolve: {
            audioSeries: AudioSeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audio-series/:id/edit',
        component: AudioSeriesUpdateComponent,
        resolve: {
            audioSeries: AudioSeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const audioSeriesPopupRoute: Routes = [
    {
        path: 'audio-series/:id/delete',
        component: AudioSeriesDeletePopupComponent,
        resolve: {
            audioSeries: AudioSeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.audioSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
