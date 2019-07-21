/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { AudioBookDetailComponent } from 'app/entities/audio-book/audio-book-detail.component';
import { AudioBook } from 'app/shared/model/audio-book.model';

describe('Component Tests', () => {
    describe('AudioBook Management Detail Component', () => {
        let comp: AudioBookDetailComponent;
        let fixture: ComponentFixture<AudioBookDetailComponent>;
        const route = ({ data: of({ audioBook: new AudioBook(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioBookDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AudioBookDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AudioBookDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.audioBook).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
