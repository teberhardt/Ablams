/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AblamsTestModule } from '../../../test.module';
import { AudioFileComponent } from 'app/entities/audio-file/audio-file.component';
import { AudioFileService } from 'app/entities/audio-file/audio-file.service';
import { AudioFile } from 'app/shared/model/audio-file.model';

describe('Component Tests', () => {
    describe('AudioFile Management Component', () => {
        let comp: AudioFileComponent;
        let fixture: ComponentFixture<AudioFileComponent>;
        let service: AudioFileService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioFileComponent],
                providers: []
            })
                .overrideTemplate(AudioFileComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AudioFileComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AudioFileService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AudioFile(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.audioFiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
