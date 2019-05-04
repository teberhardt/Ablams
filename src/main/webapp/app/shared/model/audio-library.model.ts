import { IAudioBook } from 'app/shared/model//audio-book.model';

export interface IAudioLibrary {
    id?: number;
    filepath?: string;
    audioBooks?: IAudioBook[];
}

export class AudioLibrary implements IAudioLibrary {
    constructor(public id?: number, public filepath?: string, public audioBooks?: IAudioBook[]) {}
}
