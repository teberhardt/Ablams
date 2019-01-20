/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { AudioFileDetailComponent } from 'app/entities/audio-file/audio-file-detail.component';
import { AudioFile } from 'app/shared/model/audio-file.model';

describe('Component Tests', () => {
    describe('AudioFile Management Detail Component', () => {
        let comp: AudioFileDetailComponent;
        let fixture: ComponentFixture<AudioFileDetailComponent>;
        const route = ({ data: of({ audioFile: new AudioFile(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioFileDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AudioFileDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AudioFileDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.audioFile).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
