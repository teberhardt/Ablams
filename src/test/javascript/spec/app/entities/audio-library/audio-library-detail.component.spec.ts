/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { AudioLibraryDetailComponent } from 'app/entities/audio-library/audio-library-detail.component';
import { AudioLibrary } from 'app/shared/model/audio-library.model';

describe('Component Tests', () => {
    describe('AudioLibrary Management Detail Component', () => {
        let comp: AudioLibraryDetailComponent;
        let fixture: ComponentFixture<AudioLibraryDetailComponent>;
        const route = ({ data: of({ audioLibrary: new AudioLibrary(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioLibraryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AudioLibraryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AudioLibraryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.audioLibrary).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
