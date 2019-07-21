/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AblamsTestModule } from '../../../test.module';
import { AudioBookComponent } from 'app/entities/audio-book/audio-book.component';
import { AudioBookService } from 'app/entities/audio-book/audio-book.service';
import { AudioBook } from 'app/shared/model/audio-book.model';

describe('Component Tests', () => {
    describe('AudioBook Management Component', () => {
        let comp: AudioBookComponent;
        let fixture: ComponentFixture<AudioBookComponent>;
        let service: AudioBookService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioBookComponent],
                providers: []
            })
                .overrideTemplate(AudioBookComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AudioBookComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AudioBookService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AudioBook(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.audioBooks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
