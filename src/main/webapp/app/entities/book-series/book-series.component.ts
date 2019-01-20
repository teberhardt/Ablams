import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBookSeries } from 'app/shared/model/book-series.model';
import { AccountService } from 'app/core';
import { BookSeriesService } from './book-series.service';

@Component({
    selector: 'jhi-book-series',
    templateUrl: './book-series.component.html'
})
export class BookSeriesComponent implements OnInit, OnDestroy {
    bookSeries: IBookSeries[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected bookSeriesService: BookSeriesService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.bookSeriesService.query().subscribe(
            (res: HttpResponse<IBookSeries[]>) => {
                this.bookSeries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBookSeries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBookSeries) {
        return item.id;
    }

    registerChangeInBookSeries() {
        this.eventSubscriber = this.eventManager.subscribe('bookSeriesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
