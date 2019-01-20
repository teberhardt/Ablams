import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAudioBook } from 'app/shared/model/audio-book.model';
import { AudioBookService } from './audio-book.service';

@Component({
    selector: 'jhi-audio-book-delete-dialog',
    templateUrl: './audio-book-delete-dialog.component.html'
})
export class AudioBookDeleteDialogComponent {
    audioBook: IAudioBook;

    constructor(
        protected audioBookService: AudioBookService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.audioBookService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'audioBookListModification',
                content: 'Deleted an audioBook'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-audio-book-delete-popup',
    template: ''
})
export class AudioBookDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ audioBook }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AudioBookDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.audioBook = audioBook;
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
