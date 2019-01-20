import { IAudioFile } from 'app/shared/model//audio-file.model';

export const enum Language {
    GERMAN = 'GERMAN',
    FRENCH = 'FRENCH',
    ENGLISH = 'ENGLISH',
    SPANISH = 'SPANISH'
}

export interface IAudioBook {
    id?: number;
    name?: string;
    language?: Language;
    audioFiles?: IAudioFile[];
    imageId?: number;
    seriesId?: number;
    authorId?: number;
}

export class AudioBook implements IAudioBook {
    constructor(
        public id?: number,
        public name?: string,
        public language?: Language,
        public audioFiles?: IAudioFile[],
        public imageId?: number,
        public seriesId?: number,
        public authorId?: number
    ) {}
}
