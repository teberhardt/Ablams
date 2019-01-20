/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AblamsTestModule } from '../../../test.module';
import { BookSeriesComponent } from 'app/entities/book-series/book-series.component';
import { BookSeriesService } from 'app/entities/book-series/book-series.service';
import { BookSeries } from 'app/shared/model/book-series.model';

describe('Component Tests', () => {
    describe('BookSeries Management Component', () => {
        let comp: BookSeriesComponent;
        let fixture: ComponentFixture<BookSeriesComponent>;
        let service: BookSeriesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [BookSeriesComponent],
                providers: []
            })
                .overrideTemplate(BookSeriesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BookSeriesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookSeriesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BookSeries(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bookSeries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
