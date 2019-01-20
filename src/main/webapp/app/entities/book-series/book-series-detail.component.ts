import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookSeries } from 'app/shared/model/book-series.model';

@Component({
    selector: 'jhi-book-series-detail',
    templateUrl: './book-series-detail.component.html'
})
export class BookSeriesDetailComponent implements OnInit {
    bookSeries: IBookSeries;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bookSeries }) => {
            this.bookSeries = bookSeries;
        });
    }

    previousState() {
        window.history.back();
    }
}
