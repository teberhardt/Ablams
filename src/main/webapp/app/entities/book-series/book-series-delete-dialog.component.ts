import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookSeries } from 'app/shared/model/book-series.model';
import { BookSeriesService } from './book-series.service';

@Component({
    selector: 'jhi-book-series-delete-dialog',
    templateUrl: './book-series-delete-dialog.component.html'
})
export class BookSeriesDeleteDialogComponent {
    bookSeries: IBookSeries;

    constructor(
        protected bookSeriesService: BookSeriesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bookSeriesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bookSeriesListModification',
                content: 'Deleted an bookSeries'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-book-series-delete-popup',
    template: ''
})
export class BookSeriesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bookSeries }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BookSeriesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bookSeries = bookSeries;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
