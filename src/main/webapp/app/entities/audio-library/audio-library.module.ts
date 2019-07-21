import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AblamsSharedModule } from 'app/shared';
import {
    AudioLibraryComponent,
    AudioLibraryDetailComponent,
    AudioLibraryUpdateComponent,
    AudioLibraryDeletePopupComponent,
    AudioLibraryDeleteDialogComponent,
    audioLibraryRoute,
    audioLibraryPopupRoute
} from './';

const ENTITY_STATES = [...audioLibraryRoute, ...audioLibraryPopupRoute];

@NgModule({
    imports: [AblamsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AudioLibraryComponent,
        AudioLibraryDetailComponent,
        AudioLibraryUpdateComponent,
        AudioLibraryDeleteDialogComponent,
        AudioLibraryDeletePopupComponent
    ],
    entryComponents: [
        AudioLibraryComponent,
        AudioLibraryUpdateComponent,
        AudioLibraryDeleteDialogComponent,
        AudioLibraryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AblamsAudioLibraryModule {}
