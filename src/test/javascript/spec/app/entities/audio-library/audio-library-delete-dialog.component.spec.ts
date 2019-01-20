/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AblamsTestModule } from '../../../test.module';
import { AudioLibraryDeleteDialogComponent } from 'app/entities/audio-library/audio-library-delete-dialog.component';
import { AudioLibraryService } from 'app/entities/audio-library/audio-library.service';

describe('Component Tests', () => {
    describe('AudioLibrary Management Delete Component', () => {
        let comp: AudioLibraryDeleteDialogComponent;
        let fixture: ComponentFixture<AudioLibraryDeleteDialogComponent>;
        let service: AudioLibraryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioLibraryDeleteDialogComponent]
            })
                .overrideTemplate(AudioLibraryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AudioLibraryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AudioLibraryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
