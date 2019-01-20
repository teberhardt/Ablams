/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AblamsTestModule } from '../../../test.module';
import { AudioFileDeleteDialogComponent } from 'app/entities/audio-file/audio-file-delete-dialog.component';
import { AudioFileService } from 'app/entities/audio-file/audio-file.service';

describe('Component Tests', () => {
    describe('AudioFile Management Delete Component', () => {
        let comp: AudioFileDeleteDialogComponent;
        let fixture: ComponentFixture<AudioFileDeleteDialogComponent>;
        let service: AudioFileService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioFileDeleteDialogComponent]
            })
                .overrideTemplate(AudioFileDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AudioFileDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AudioFileService);
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
