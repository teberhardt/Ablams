import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgressable } from 'app/shared/model/progressable.model';

@Component({
    selector: 'jhi-progressable-detail',
    templateUrl: './progressable-detail.component.html'
})
export class ProgressableDetailComponent implements OnInit {
    progressable: IProgressable;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ progressable }) => {
            this.progressable = progressable;
        });
    }

    previousState() {
        window.history.back();
    }
}
