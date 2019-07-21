import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAudioLibrary } from 'app/shared/model/audio-library.model';
import { AudioLibraryService } from './audio-library.service';

@Component({
    selector: 'jhi-audio-library-update',
    templateUrl: './audio-library-update.component.html'
})
export class AudioLibraryUpdateComponent implements OnInit {
    audioLibrary: IAudioLibrary;
    isSaving: boolean;

    constructor(protected audioLibraryService: AudioLibraryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ audioLibrary }) => {
            this.audioLibrary = audioLibrary;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.audioLibrary.id !== undefined) {
            this.subscribeToSaveResponse(this.audioLibraryService.update(this.audioLibrary));
        } else {
            this.subscribeToSaveResponse(this.audioLibraryService.create(this.audioLibrary));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAudioLibrary>>) {
        result.subscribe((res: HttpResponse<IAudioLibrary>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
