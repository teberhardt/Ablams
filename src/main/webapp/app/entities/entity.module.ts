import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AblamsAuthorModule } from './author/author.module';
import { AblamsImageModule } from './image/image.module';
import { AblamsAudioSeriesModule } from './audio-series/audio-series.module';
import { AblamsAudioLibraryModule } from './audio-library/audio-library.module';
import { AblamsAudioFileModule } from './audio-file/audio-file.module';
import { AblamsAudioBookModule } from './audio-book/audio-book.module';
import { AblamsProgressableModule } from './progressable/progressable.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AblamsAuthorModule,
        AblamsImageModule,
        AblamsAudioSeriesModule,
        AblamsAudioLibraryModule,
        AblamsAudioFileModule,
        AblamsAudioBookModule,
        AblamsProgressableModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AblamsEntityModule {}
