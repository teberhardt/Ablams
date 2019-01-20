import { IAudioBook } from 'app/shared/model//audio-book.model';
import { IBookSeries } from 'app/shared/model//book-series.model';

export interface IAuthor {
    id?: number;
    name?: string;
    lastName?: string;
    audioBooks?: IAudioBook[];
    bookSeries?: IBookSeries[];
    imageId?: number;
}

export class Author implements IAuthor {
    constructor(
        public id?: number,
        public name?: string,
        public lastName?: string,
        public audioBooks?: IAudioBook[],
        public bookSeries?: IBookSeries[],
        public imageId?: number
    ) {}
}
