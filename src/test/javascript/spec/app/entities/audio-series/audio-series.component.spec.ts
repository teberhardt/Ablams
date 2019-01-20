/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AblamsTestModule } from '../../../test.module';
import { AudioSeriesComponent } from 'app/entities/audio-series/audio-series.component';
import { AudioSeriesService } from 'app/entities/audio-series/audio-series.service';
import { AudioSeries } from 'app/shared/model/audio-series.model';

describe('Component Tests', () => {
    describe('AudioSeries Management Component', () => {
        let comp: AudioSeriesComponent;
        let fixture: ComponentFixture<AudioSeriesComponent>;
        let service: AudioSeriesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioSeriesComponent],
                providers: []
            })
                .overrideTemplate(AudioSeriesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AudioSeriesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AudioSeriesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AudioSeries(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.audioSeries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
