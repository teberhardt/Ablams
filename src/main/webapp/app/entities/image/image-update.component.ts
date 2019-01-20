import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IImage } from 'app/shared/model/image.model';
import { ImageService } from './image.service';
import { IAudioBook } from 'app/shared/model/audio-book.model';
import { AudioBookService } from 'app/entities/audio-book';
import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from 'app/entities/author';

@Component({
    selector: 'jhi-image-update',
    templateUrl: './image-update.component.html'
})
export class ImageUpdateComponent implements OnInit {
    image: IImage;
    isSaving: boolean;

    audiobooks: IAudioBook[];

    authors: IAuthor[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected imageService: ImageService,
        protected audioBookService: AudioBookService,
        protected authorService: AuthorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ image }) => {
            this.image = image;
        });
        this.audioBookService.query({ filter: 'image-is-null' }).subscribe(
            (res: HttpResponse<IAudioBook[]>) => {
                if (!this.image.audioBookId) {
                    this.audiobooks = res.body;
                } else {
                    this.audioBookService.find(this.image.audioBookId).subscribe(
                        (subRes: HttpResponse<IAudioBook>) => {
                            this.audiobooks = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.authorService.query({ filter: 'image-is-null' }).subscribe(
            (res: HttpResponse<IAuthor[]>) => {
                if (!this.image.authorId) {
                    this.authors = res.body;
                } else {
                    this.authorService.find(this.image.authorId).subscribe(
                        (subRes: HttpResponse<IAuthor>) => {
                            this.authors = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.image.id !== undefined) {
            this.subscribeToSaveResponse(this.imageService.update(this.image));
        } else {
            this.subscribeToSaveResponse(this.imageService.create(this.image));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IImage>>) {
        result.subscribe((res: HttpResponse<IImage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAudioBookById(index: number, item: IAudioBook) {
        return item.id;
    }

    trackAuthorById(index: number, item: IAuthor) {
        return item.id;
    }
}
