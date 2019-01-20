/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AblamsTestModule } from '../../../test.module';
import { BookSeriesDeleteDialogComponent } from 'app/entities/book-series/book-series-delete-dialog.component';
import { BookSeriesService } from 'app/entities/book-series/book-series.service';

describe('Component Tests', () => {
    describe('BookSeries Management Delete Component', () => {
        let comp: BookSeriesDeleteDialogComponent;
        let fixture: ComponentFixture<BookSeriesDeleteDialogComponent>;
        let service: BookSeriesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [BookSeriesDeleteDialogComponent]
            })
                .overrideTemplate(BookSeriesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BookSeriesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookSeriesService);
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
