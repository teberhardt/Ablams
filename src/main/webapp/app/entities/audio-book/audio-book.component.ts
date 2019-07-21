import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAudioBook } from 'app/shared/model/audio-book.model';
import { AccountService } from 'app/core';
import { AudioBookService } from './audio-book.service';

@Component({
    selector: 'jhi-audio-book',
    templateUrl: './audio-book.component.html'
})
export class AudioBookComponent implements OnInit, OnDestroy {
    audioBooks: IAudioBook[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected audioBookService: AudioBookService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.audioBookService.query().subscribe(
            (res: HttpResponse<IAudioBook[]>) => {
                this.audioBooks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAudioBooks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAudioBook) {
        return item.id;
    }

    registerChangeInAudioBooks() {
        this.eventSubscriber = this.eventManager.subscribe('audioBookListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
