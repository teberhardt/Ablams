import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AblamsSharedModule } from 'app/shared';
import {
    ProgressableComponent,
    ProgressableDetailComponent,
    ProgressableUpdateComponent,
    ProgressableDeletePopupComponent,
    ProgressableDeleteDialogComponent,
    progressableRoute,
    progressablePopupRoute
} from './';

const ENTITY_STATES = [...progressableRoute, ...progressablePopupRoute];

@NgModule({
    imports: [AblamsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProgressableComponent,
        ProgressableDetailComponent,
        ProgressableUpdateComponent,
        ProgressableDeleteDialogComponent,
        ProgressableDeletePopupComponent
    ],
    entryComponents: [
        ProgressableComponent,
        ProgressableUpdateComponent,
        ProgressableDeleteDialogComponent,
        ProgressableDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AblamsProgressableModule {}
