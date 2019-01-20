import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAudioLibrary } from 'app/shared/model/audio-library.model';
import { AccountService } from 'app/core';
import { AudioLibraryService } from './audio-library.service';

@Component({
    selector: 'jhi-audio-library',
    templateUrl: './audio-library.component.html'
})
export class AudioLibraryComponent implements OnInit, OnDestroy {
    audioLibraries: IAudioLibrary[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected audioLibraryService: AudioLibraryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.audioLibraryService.query().subscribe(
            (res: HttpResponse<IAudioLibrary[]>) => {
                this.audioLibraries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAudioLibraries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAudioLibrary) {
        return item.id;
    }

    registerChangeInAudioLibraries() {
        this.eventSubscriber = this.eventManager.subscribe('audioLibraryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
