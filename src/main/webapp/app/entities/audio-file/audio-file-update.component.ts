import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAudioFile } from 'app/shared/model/audio-file.model';
import { AudioFileService } from './audio-file.service';
import { IAudioBook } from 'app/shared/model/audio-book.model';
import { AudioBookService } from 'app/entities/audio-book';
import { IProgressable } from 'app/shared/model/progressable.model';
import { ProgressableService } from 'app/entities/progressable';

@Component({
    selector: 'jhi-audio-file-update',
    templateUrl: './audio-file-update.component.html'
})
export class AudioFileUpdateComponent implements OnInit {
    audioFile: IAudioFile;
    isSaving: boolean;

    audiobooks: IAudioBook[];

    progressables: IProgressable[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected audioFileService: AudioFileService,
        protected audioBookService: AudioBookService,
        protected progressableService: ProgressableService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ audioFile }) => {
            this.audioFile = audioFile;
        });
        this.audioBookService.query().subscribe(
            (res: HttpResponse<IAudioBook[]>) => {
                this.audiobooks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.progressableService.query().subscribe(
            (res: HttpResponse<IProgressable[]>) => {
                this.progressables = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.audioFile.id !== undefined) {
            this.subscribeToSaveResponse(this.audioFileService.update(this.audioFile));
        } else {
            this.subscribeToSaveResponse(this.audioFileService.create(this.audioFile));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAudioFile>>) {
        result.subscribe((res: HttpResponse<IAudioFile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAudioBookById(index: number, item: IAudioBook) {
        return item.id;
    }

    trackProgressableById(index: number, item: IProgressable) {
        return item.id;
    }
}
