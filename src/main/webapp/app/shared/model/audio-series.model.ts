import { IAudioBook } from 'app/shared/model//audio-book.model';

export interface IAudioSeries {
    id?: number;
    seriesName?: string;
    audioBooks?: IAudioBook[];
    authorId?: number;
}

export class AudioSeries implements IAudioSeries {
    constructor(public id?: number, public seriesName?: string, public audioBooks?: IAudioBook[], public authorId?: number) {}
}
