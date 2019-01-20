import { IAudioFile } from 'app/shared/model//audio-file.model';

export interface IAudioLibrary {
    id?: number;
    filepath?: string;
    audioFiles?: IAudioFile[];
}

export class AudioLibrary implements IAudioLibrary {
    constructor(public id?: number, public filepath?: string, public audioFiles?: IAudioFile[]) {}
}
