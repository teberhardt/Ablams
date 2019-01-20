import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBookSeries } from 'app/shared/model/book-series.model';
import { BookSeriesService } from './book-series.service';
import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from 'app/entities/author';

@Component({
    selector: 'jhi-book-series-update',
    templateUrl: './book-series-update.component.html'
})
export class BookSeriesUpdateComponent implements OnInit {
    bookSeries: IBookSeries;
    isSaving: boolean;

    authors: IAuthor[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bookSeriesService: BookSeriesService,
        protected authorService: AuthorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bookSeries }) => {
            this.bookSeries = bookSeries;
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
        if (this.bookSeries.id !== undefined) {
            this.subscribeToSaveResponse(this.bookSeriesService.update(this.bookSeries));
        } else {
            this.subscribeToSaveResponse(this.bookSeriesService.create(this.bookSeries));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookSeries>>) {
        result.subscribe((res: HttpResponse<IBookSeries>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
