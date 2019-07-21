import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IProgressable } from 'app/shared/model/progressable.model';
import { ProgressableService } from './progressable.service';

@Component({
    selector: 'jhi-progressable-update',
    templateUrl: './progressable-update.component.html'
})
export class ProgressableUpdateComponent implements OnInit {
    progressable: IProgressable;
    isSaving: boolean;

    constructor(protected progressableService: ProgressableService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ progressable }) => {
            this.progressable = progressable;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.progressable.id !== undefined) {
            this.subscribeToSaveResponse(this.progressableService.update(this.progressable));
        } else {
            this.subscribeToSaveResponse(this.progressableService.create(this.progressable));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgressable>>) {
        result.subscribe((res: HttpResponse<IProgressable>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
