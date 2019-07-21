/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AblamsTestModule } from '../../../test.module';
import { AudioFileUpdateComponent } from 'app/entities/audio-file/audio-file-update.component';
import { AudioFileService } from 'app/entities/audio-file/audio-file.service';
import { AudioFile } from 'app/shared/model/audio-file.model';

describe('Component Tests', () => {
    describe('AudioFile Management Update Component', () => {
        let comp: AudioFileUpdateComponent;
        let fixture: ComponentFixture<AudioFileUpdateComponent>;
        let service: AudioFileService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AblamsTestModule],
                declarations: [AudioFileUpdateComponent]
            })
                .overrideTemplate(AudioFileUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AudioFileUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AudioFileService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AudioFile(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.audioFile = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AudioFile();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.audioFile = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
