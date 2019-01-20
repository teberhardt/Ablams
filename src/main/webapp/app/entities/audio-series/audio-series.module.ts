import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AblamsSharedModule } from 'app/shared';
import {
    AudioSeriesComponent,
    AudioSeriesDetailComponent,
    AudioSeriesUpdateComponent,
    AudioSeriesDeletePopupComponent,
    AudioSeriesDeleteDialogComponent,
    audioSeriesRoute,
    audioSeriesPopupRoute
} from './';

const ENTITY_STATES = [...audioSeriesRoute, ...audioSeriesPopupRoute];

@NgModule({
    imports: [AblamsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AudioSeriesComponent,
        AudioSeriesDetailComponent,
        AudioSeriesUpdateComponent,
        AudioSeriesDeleteDialogComponent,
        AudioSeriesDeletePopupComponent
    ],
    entryComponents: [AudioSeriesComponent, AudioSeriesUpdateComponent, AudioSeriesDeleteDialogComponent, AudioSeriesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AblamsAudioSeriesModule {}
