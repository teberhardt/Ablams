/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AblamsTestModule } from '../../../test.module';
import { AudioSeriesDeleteDialogComponent } from 'app/entities/audio-series/audio-series-delete-dialog.component';
import { AudioSeriesService } from 'app/entities/audio-series/audio-series.service';

describe('Component Tests', () => {
    describe('AudioSeries Management Delete Component', () => {
        let comp: AudioSeriesDeleteDialogComponent;
        let fixture: ComponentFixture<AudioSeriesDeleteDialogComponent>;
        let service: AudioSeriesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioSeriesDeleteDialogComponent]
            })
                .overrideTemplate(AudioSeriesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AudioSeriesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AudioSeriesService);
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
