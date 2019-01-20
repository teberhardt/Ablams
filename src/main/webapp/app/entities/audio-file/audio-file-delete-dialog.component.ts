import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAudioFile } from 'app/shared/model/audio-file.model';
import { AudioFileService } from './audio-file.service';

@Component({
    selector: 'jhi-audio-file-delete-dialog',
    templateUrl: './audio-file-delete-dialog.component.html'
})
export class AudioFileDeleteDialogComponent {
    audioFile: IAudioFile;

    constructor(
        protected audioFileService: AudioFileService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.audioFileService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'audioFileListModification',
                content: 'Deleted an audioFile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-audio-file-delete-popup',
    template: ''
})
export class AudioFileDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ audioFile }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AudioFileDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.audioFile = audioFile;
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
