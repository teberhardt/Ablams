import { IAudioBook } from 'app/shared/model//audio-book.model';
import { IAudioSeries } from 'app/shared/model//audio-series.model';

export interface IAuthor {
    id?: number;
    name?: string;
    lastName?: string;
    audioBooks?: IAudioBook[];
    audioSeries?: IAudioSeries[];
    imageId?: number;
}

export class Author implements IAuthor {
    constructor(
        public id?: number,
        public name?: string,
        public lastName?: string,
        public audioBooks?: IAudioBook[],
        public audioSeries?: IAudioSeries[],
        public imageId?: number
    ) {}
}
