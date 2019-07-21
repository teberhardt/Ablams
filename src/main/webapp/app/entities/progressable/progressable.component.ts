import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProgressable } from 'app/shared/model/progressable.model';
import { AccountService } from 'app/core';
import { ProgressableService } from './progressable.service';

@Component({
    selector: 'jhi-progressable',
    templateUrl: './progressable.component.html'
})
export class ProgressableComponent implements OnInit, OnDestroy {
    progressables: IProgressable[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected progressableService: ProgressableService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.progressableService.query().subscribe(
            (res: HttpResponse<IProgressable[]>) => {
                this.progressables = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProgressables();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProgressable) {
        return item.id;
    }

    registerChangeInProgressables() {
        this.eventSubscriber = this.eventManager.subscribe('progressableListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
