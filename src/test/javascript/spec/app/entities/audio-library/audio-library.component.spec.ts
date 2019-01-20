/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AblamsTestModule } from '../../../test.module';
import { AudioLibraryComponent } from 'app/entities/audio-library/audio-library.component';
import { AudioLibraryService } from 'app/entities/audio-library/audio-library.service';
import { AudioLibrary } from 'app/shared/model/audio-library.model';

describe('Component Tests', () => {
    describe('AudioLibrary Management Component', () => {
        let comp: AudioLibraryComponent;
        let fixture: ComponentFixture<AudioLibraryComponent>;
        let service: AudioLibraryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioLibraryComponent],
                providers: []
            })
                .overrideTemplate(AudioLibraryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AudioLibraryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AudioLibraryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AudioLibrary(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.audioLibraries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
