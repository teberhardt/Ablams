import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAudioBook } from 'app/shared/model/audio-book.model';

@Component({
    selector: 'jhi-audio-book-detail',
    templateUrl: './audio-book-detail.component.html'
})
export class AudioBookDetailComponent implements OnInit {
    audioBook: IAudioBook;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ audioBook }) => {
            this.audioBook = audioBook;
        });
    }

    previousState() {
        window.history.back();
    }
}
