import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Progressable } from 'app/shared/model/progressable.model';
import { ProgressableService } from './progressable.service';
import { ProgressableComponent } from './progressable.component';
import { ProgressableDetailComponent } from './progressable-detail.component';
import { ProgressableUpdateComponent } from './progressable-update.component';
import { ProgressableDeletePopupComponent } from './progressable-delete-dialog.component';
import { IProgressable } from 'app/shared/model/progressable.model';

@Injectable({ providedIn: 'root' })
export class ProgressableResolve implements Resolve<IProgressable> {
    constructor(private service: ProgressableService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Progressable> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Progressable>) => response.ok),
                map((progressable: HttpResponse<Progressable>) => progressable.body)
            );
        }
        return of(new Progressable());
    }
}

export const progressableRoute: Routes = [
    {
        path: 'progressable',
        component: ProgressableComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.progressable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'progressable/:id/view',
        component: ProgressableDetailComponent,
        resolve: {
            progressable: ProgressableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.progressable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'progressable/new',
        component: ProgressableUpdateComponent,
        resolve: {
            progressable: ProgressableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.progressable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'progressable/:id/edit',
        component: ProgressableUpdateComponent,
        resolve: {
            progressable: ProgressableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.progressable.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const progressablePopupRoute: Routes = [
    {
        path: 'progressable/:id/delete',
        component: ProgressableDeletePopupComponent,
        resolve: {
            progressable: ProgressableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ablamsApp.progressable.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
