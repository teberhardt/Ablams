/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { BookSeriesUpdateComponent } from 'app/entities/book-series/book-series-update.component';
import { BookSeriesService } from 'app/entities/book-series/book-series.service';
import { BookSeries } from 'app/shared/model/book-series.model';

describe('Component Tests', () => {
    describe('BookSeries Management Update Component', () => {
        let comp: BookSeriesUpdateComponent;
        let fixture: ComponentFixture<BookSeriesUpdateComponent>;
        let service: BookSeriesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [BookSeriesUpdateComponent]
            })
                .overrideTemplate(BookSeriesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BookSeriesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookSeriesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new BookSeries(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.bookSeries = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new BookSeries();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.bookSeries = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
