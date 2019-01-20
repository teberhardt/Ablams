import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AblamsSharedModule } from 'app/shared';
import {
    BookSeriesComponent,
    BookSeriesDetailComponent,
    BookSeriesUpdateComponent,
    BookSeriesDeletePopupComponent,
    BookSeriesDeleteDialogComponent,
    bookSeriesRoute,
    bookSeriesPopupRoute
} from './';

const ENTITY_STATES = [...bookSeriesRoute, ...bookSeriesPopupRoute];

@NgModule({
    imports: [AblamsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BookSeriesComponent,
        BookSeriesDetailComponent,
        BookSeriesUpdateComponent,
        BookSeriesDeleteDialogComponent,
        BookSeriesDeletePopupComponent
    ],
    entryComponents: [BookSeriesComponent, BookSeriesUpdateComponent, BookSeriesDeleteDialogComponent, BookSeriesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AblamsBookSeriesModule {}
