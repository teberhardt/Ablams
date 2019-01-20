import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BookSeries } from 'app/shared/model/book-series.model';
import { BookSeriesService } from './book-series.service';
import { BookSeriesComponent } from './book-series.component';
import { BookSeriesDetailComponent } from './book-series-detail.component';
import { BookSeriesUpdateComponent } from './book-series-update.component';
import { BookSeriesDeletePopupComponent } from './book-series-delete-dialog.component';
import { IBookSeries } from 'app/shared/model/book-series.model';

@Injectable({ providedIn: 'root' })
export class BookSeriesResolve implements Resolve<IBookSeries> {
    constructor(private service: BookSeriesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BookSeries> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BookSeries>) => response.ok),
                map((bookSeries: HttpResponse<BookSeries>) => bookSeries.body)
            );
        }
        return of(new BookSeries());
    }
}

export const bookSeriesRoute: Routes = [
    {
        path: 'book-series',
        component: BookSeriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.bookSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'book-series/:id/view',
        component: BookSeriesDetailComponent,
        resolve: {
            bookSeries: BookSeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.bookSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'book-series/new',
        component: BookSeriesUpdateComponent,
        resolve: {
            bookSeries: BookSeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.bookSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'book-series/:id/edit',
        component: BookSeriesUpdateComponent,
        resolve: {
            bookSeries: BookSeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.bookSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bookSeriesPopupRoute: Routes = [
    {
        path: 'book-series/:id/delete',
        component: BookSeriesDeletePopupComponent,
        resolve: {
            bookSeries: BookSeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.bookSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
