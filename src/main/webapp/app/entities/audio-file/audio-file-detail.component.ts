import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAudioFile } from 'app/shared/model/audio-file.model';

@Component({
    selector: 'jhi-audio-file-detail',
    templateUrl: './audio-file-detail.component.html'
})
export class AudioFileDetailComponent implements OnInit {
    audioFile: IAudioFile;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ audioFile }) => {
            this.audioFile = audioFile;
        });
    }

    previousState() {
        window.history.back();
    }
}
