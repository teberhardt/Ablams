import { IAudioBook } from 'app/shared/model//audio-book.model';

export interface IBookSeries {
    id?: number;
    seriesName?: string;
    audioBooks?: IAudioBook[];
    authorId?: number;
}

export class BookSeries implements IBookSeries {
    constructor(public id?: number, public seriesName?: string, public audioBooks?: IAudioBook[], public authorId?: number) {}
}
