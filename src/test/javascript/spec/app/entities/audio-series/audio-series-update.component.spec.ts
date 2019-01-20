/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { AudioSeriesUpdateComponent } from 'app/entities/audio-series/audio-series-update.component';
import { AudioSeriesService } from 'app/entities/audio-series/audio-series.service';
import { AudioSeries } from 'app/shared/model/audio-series.model';

describe('Component Tests', () => {
    describe('AudioSeries Management Update Component', () => {
        let comp: AudioSeriesUpdateComponent;
        let fixture: ComponentFixture<AudioSeriesUpdateComponent>;
        let service: AudioSeriesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioSeriesUpdateComponent]
            })
                .overrideTemplate(AudioSeriesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AudioSeriesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AudioSeriesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AudioSeries(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.audioSeries = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AudioSeries();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.audioSeries = entity;
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
