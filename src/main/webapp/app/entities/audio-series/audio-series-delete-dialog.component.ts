import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAudioSeries } from 'app/shared/model/audio-series.model';
import { AudioSeriesService } from './audio-series.service';

@Component({
    selector: 'jhi-audio-series-delete-dialog',
    templateUrl: './audio-series-delete-dialog.component.html'
})
export class AudioSeriesDeleteDialogComponent {
    audioSeries: IAudioSeries;

    constructor(
        protected audioSeriesService: AudioSeriesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.audioSeriesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'audioSeriesListModification',
                content: 'Deleted an audioSeries'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-audio-series-delete-popup',
    template: ''
})
export class AudioSeriesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ audioSeries }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AudioSeriesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.audioSeries = audioSeries;
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
