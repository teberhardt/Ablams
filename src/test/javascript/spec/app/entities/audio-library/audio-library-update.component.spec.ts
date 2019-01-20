/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { AudioLibraryUpdateComponent } from 'app/entities/audio-library/audio-library-update.component';
import { AudioLibraryService } from 'app/entities/audio-library/audio-library.service';
import { AudioLibrary } from 'app/shared/model/audio-library.model';

describe('Component Tests', () => {
    describe('AudioLibrary Management Update Component', () => {
        let comp: AudioLibraryUpdateComponent;
        let fixture: ComponentFixture<AudioLibraryUpdateComponent>;
        let service: AudioLibraryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioLibraryUpdateComponent]
            })
                .overrideTemplate(AudioLibraryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AudioLibraryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AudioLibraryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AudioLibrary(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.audioLibrary = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AudioLibrary();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.audioLibrary = entity;
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
