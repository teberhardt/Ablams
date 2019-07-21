/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { AudioSeriesDetailComponent } from 'app/entities/audio-series/audio-series-detail.component';
import { AudioSeries } from 'app/shared/model/audio-series.model';

describe('Component Tests', () => {
    describe('AudioSeries Management Detail Component', () => {
        let comp: AudioSeriesDetailComponent;
        let fixture: ComponentFixture<AudioSeriesDetailComponent>;
        const route = ({ data: of({ audioSeries: new AudioSeries(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioSeriesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AudioSeriesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AudioSeriesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.audioSeries).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
