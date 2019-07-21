import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AblamsSharedModule } from 'app/shared';
import {
    AudioFileComponent,
    AudioFileDetailComponent,
    AudioFileUpdateComponent,
    AudioFileDeletePopupComponent,
    AudioFileDeleteDialogComponent,
    audioFileRoute,
    audioFilePopupRoute
} from './';

const ENTITY_STATES = [...audioFileRoute, ...audioFilePopupRoute];

@NgModule({
    imports: [AblamsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AudioFileComponent,
        AudioFileDetailComponent,
        AudioFileUpdateComponent,
        AudioFileDeleteDialogComponent,
        AudioFileDeletePopupComponent
    ],
    entryComponents: [AudioFileComponent, AudioFileUpdateComponent, AudioFileDeleteDialogComponent, AudioFileDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AblamsAudioFileModule {}
