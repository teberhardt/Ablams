import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAudioLibrary } from 'app/shared/model/audio-library.model';

@Component({
    selector: 'jhi-audio-library-detail',
    templateUrl: './audio-library-detail.component.html'
})
export class AudioLibraryDetailComponent implements OnInit {
    audioLibrary: IAudioLibrary;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ audioLibrary }) => {
            this.audioLibrary = audioLibrary;
        });
    }

    previousState() {
        window.history.back();
    }
}
