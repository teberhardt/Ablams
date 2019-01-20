import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAudioSeries } from 'app/shared/model/audio-series.model';
import { AccountService } from 'app/core';
import { AudioSeriesService } from './audio-series.service';

@Component({
    selector: 'jhi-audio-series',
    templateUrl: './audio-series.component.html'
})
export class AudioSeriesComponent implements OnInit, OnDestroy {
    audioSeries: IAudioSeries[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected audioSeriesService: AudioSeriesService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.audioSeriesService.query().subscribe(
            (res: HttpResponse<IAudioSeries[]>) => {
                this.audioSeries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAudioSeries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAudioSeries) {
        return item.id;
    }

    registerChangeInAudioSeries() {
        this.eventSubscriber = this.eventManager.subscribe('audioSeriesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
