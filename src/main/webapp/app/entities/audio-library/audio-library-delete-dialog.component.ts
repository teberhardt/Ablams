import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAudioLibrary } from 'app/shared/model/audio-library.model';
import { AudioLibraryService } from './audio-library.service';

@Component({
    selector: 'jhi-audio-library-delete-dialog',
    templateUrl: './audio-library-delete-dialog.component.html'
})
export class AudioLibraryDeleteDialogComponent {
    audioLibrary: IAudioLibrary;

    constructor(
        protected audioLibraryService: AudioLibraryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.audioLibraryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'audioLibraryListModification',
                content: 'Deleted an audioLibrary'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-audio-library-delete-popup',
    template: ''
})
export class AudioLibraryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ audioLibrary }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AudioLibraryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.audioLibrary = audioLibrary;
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
