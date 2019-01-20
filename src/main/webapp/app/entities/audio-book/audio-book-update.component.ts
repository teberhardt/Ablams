import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAudioBook } from 'app/shared/model/audio-book.model';
import { AudioBookService } from './audio-book.service';
import { IImage } from 'app/shared/model/image.model';
import { ImageService } from 'app/entities/image';
import { IAudioSeries } from 'app/shared/model/audio-series.model';
import { AudioSeriesService } from 'app/entities/audio-series';
import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from 'app/entities/author';

@Component({
    selector: 'jhi-audio-book-update',
    templateUrl: './audio-book-update.component.html'
})
export class AudioBookUpdateComponent implements OnInit {
    audioBook: IAudioBook;
    isSaving: boolean;

    images: IImage[];

    audioseries: IAudioSeries[];

    authors: IAuthor[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected audioBookService: AudioBookService,
        protected imageService: ImageService,
        protected audioSeriesService: AudioSeriesService,
        protected authorService: AuthorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ audioBook }) => {
            this.audioBook = audioBook;
        });
        this.imageService.query().subscribe(
            (res: HttpResponse<IImage[]>) => {
                this.images = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.audioSeriesService.query().subscribe(
            (res: HttpResponse<IAudioSeries[]>) => {
                this.audioseries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.authorService.query().subscribe(
            (res: HttpResponse<IAuthor[]>) => {
                this.authors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.audioBook.id !== undefined) {
            this.subscribeToSaveResponse(this.audioBookService.update(this.audioBook));
        } else {
            this.subscribeToSaveResponse(this.audioBookService.create(this.audioBook));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAudioBook>>) {
        result.subscribe((res: HttpResponse<IAudioBook>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackImageById(index: number, item: IImage) {
        return item.id;
    }

    trackAudioSeriesById(index: number, item: IAudioSeries) {
        return item.id;
    }

    trackAuthorById(index: number, item: IAuthor) {
        return item.id;
    }
}
