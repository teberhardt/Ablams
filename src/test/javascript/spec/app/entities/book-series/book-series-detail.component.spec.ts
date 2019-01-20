/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { BookSeriesDetailComponent } from 'app/entities/book-series/book-series-detail.component';
import { BookSeries } from 'app/shared/model/book-series.model';

describe('Component Tests', () => {
    describe('BookSeries Management Detail Component', () => {
        let comp: BookSeriesDetailComponent;
        let fixture: ComponentFixture<BookSeriesDetailComponent>;
        const route = ({ data: of({ bookSeries: new BookSeries(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [BookSeriesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BookSeriesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BookSeriesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bookSeries).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
