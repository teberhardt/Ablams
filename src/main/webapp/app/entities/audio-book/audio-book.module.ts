import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AblamsSharedModule } from 'app/shared';
import {
    AudioBookComponent,
    AudioBookDetailComponent,
    AudioBookUpdateComponent,
    AudioBookDeletePopupComponent,
    AudioBookDeleteDialogComponent,
    audioBookRoute,
    audioBookPopupRoute
} from './';

const ENTITY_STATES = [...audioBookRoute, ...audioBookPopupRoute];

@NgModule({
    imports: [AblamsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AudioBookComponent,
        AudioBookDetailComponent,
        AudioBookUpdateComponent,
        AudioBookDeleteDialogComponent,
        AudioBookDeletePopupComponent
    ],
    entryComponents: [AudioBookComponent, AudioBookUpdateComponent, AudioBookDeleteDialogComponent, AudioBookDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AblamsAudioBookModule {}
