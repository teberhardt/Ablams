/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { AudioBookUpdateComponent } from 'app/entities/audio-book/audio-book-update.component';
import { AudioBookService } from 'app/entities/audio-book/audio-book.service';
import { AudioBook } from 'app/shared/model/audio-book.model';

describe('Component Tests', () => {
    describe('AudioBook Management Update Component', () => {
        let comp: AudioBookUpdateComponent;
        let fixture: ComponentFixture<AudioBookUpdateComponent>;
        let service: AudioBookService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioBookUpdateComponent]
            })
                .overrideTemplate(AudioBookUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AudioBookUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AudioBookService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AudioBook(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.audioBook = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AudioBook();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.audioBook = entity;
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
