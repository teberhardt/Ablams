import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAudioSeries } from 'app/shared/model/audio-series.model';
import { AudioSeriesService } from './audio-series.service';
import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from 'app/entities/author';

@Component({
    selector: 'jhi-audio-series-update',
    templateUrl: './audio-series-update.component.html'
})
export class AudioSeriesUpdateComponent implements OnInit {
    audioSeries: IAudioSeries;
    isSaving: boolean;

    authors: IAuthor[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected audioSeriesService: AudioSeriesService,
        protected authorService: AuthorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ audioSeries }) => {
            this.audioSeries = audioSeries;
        });
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
        if (this.audioSeries.id !== undefined) {
            this.subscribeToSaveResponse(this.audioSeriesService.update(this.audioSeries));
        } else {
            this.subscribeToSaveResponse(this.audioSeriesService.create(this.audioSeries));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAudioSeries>>) {
        result.subscribe((res: HttpResponse<IAudioSeries>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAuthorById(index: number, item: IAuthor) {
        return item.id;
    }
}
