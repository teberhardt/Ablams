import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAudioSeries } from 'app/shared/model/audio-series.model';

@Component({
    selector: 'jhi-audio-series-detail',
    templateUrl: './audio-series-detail.component.html'
})
export class AudioSeriesDetailComponent implements OnInit {
    audioSeries: IAudioSeries;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ audioSeries }) => {
            this.audioSeries = audioSeries;
        });
    }

    previousState() {
        window.history.back();
    }
}
