/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AblamsTestModule } from '../../../test.module';
import { ProgressableComponent } from 'app/entities/progressable/progressable.component';
import { ProgressableService } from 'app/entities/progressable/progressable.service';
import { Progressable } from 'app/shared/model/progressable.model';

describe('Component Tests', () => {
    describe('Progressable Management Component', () => {
        let comp: ProgressableComponent;
        let fixture: ComponentFixture<ProgressableComponent>;
        let service: ProgressableService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [ProgressableComponent],
                providers: []
            })
                .overrideTemplate(ProgressableComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProgressableComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProgressableService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Progressable(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.progressables[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
