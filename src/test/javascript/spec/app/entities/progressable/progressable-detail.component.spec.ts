/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { ProgressableDetailComponent } from 'app/entities/progressable/progressable-detail.component';
import { Progressable } from 'app/shared/model/progressable.model';

describe('Component Tests', () => {
    describe('Progressable Management Detail Component', () => {
        let comp: ProgressableDetailComponent;
        let fixture: ComponentFixture<ProgressableDetailComponent>;
        const route = ({ data: of({ progressable: new Progressable(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [ProgressableDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProgressableDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProgressableDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.progressable).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
