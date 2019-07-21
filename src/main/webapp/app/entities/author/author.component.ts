import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAuthor } from 'app/shared/model/author.model';
import { AccountService } from 'app/core';
import { AuthorService } from './author.service';

@Component({
    selector: 'jhi-author',
    templateUrl: './author.component.html'
})
export class AuthorComponent implements OnInit, OnDestroy {
    authors: IAuthor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected authorService: AuthorService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.authorService.query().subscribe(
            (res: HttpResponse<IAuthor[]>) => {
                this.authors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAuthors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAuthor) {
        return item.id;
    }

    registerChangeInAuthors() {
        this.eventSubscriber = this.eventManager.subscribe('authorListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
