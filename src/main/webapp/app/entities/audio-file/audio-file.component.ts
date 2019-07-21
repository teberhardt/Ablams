import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAudioFile } from 'app/shared/model/audio-file.model';
import { AccountService } from 'app/core';
import { AudioFileService } from './audio-file.service';

@Component({
    selector: 'jhi-audio-file',
    templateUrl: './audio-file.component.html'
})
export class AudioFileComponent implements OnInit, OnDestroy {
    audioFiles: IAudioFile[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected audioFileService: AudioFileService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.audioFileService.query().subscribe(
            (res: HttpResponse<IAudioFile[]>) => {
                this.audioFiles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAudioFiles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAudioFile) {
        return item.id;
    }

    registerChangeInAudioFiles() {
        this.eventSubscriber = this.eventManager.subscribe('audioFileListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
