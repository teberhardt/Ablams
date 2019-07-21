/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { ProgressableUpdateComponent } from 'app/entities/progressable/progressable-update.component';
import { ProgressableService } from 'app/entities/progressable/progressable.service';
import { Progressable } from 'app/shared/model/progressable.model';

describe('Component Tests', () => {
    describe('Progressable Management Update Component', () => {
        let comp: ProgressableUpdateComponent;
        let fixture: ComponentFixture<ProgressableUpdateComponent>;
        let service: ProgressableService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [ProgressableUpdateComponent]
            })
                .overrideTemplate(ProgressableUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProgressableUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProgressableService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Progressable(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.progressable = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Progressable();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.progressable = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
