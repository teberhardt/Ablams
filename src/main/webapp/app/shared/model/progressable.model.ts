import { IAudioFile } from 'app/shared/model//audio-file.model';

export interface IProgressable {
    id?: number;
    progress?: number;
    duration?: number;
    audioFiles?: IAudioFile[];
}

export class Progressable implements IProgressable {
    constructor(public id?: number, public progress?: number, public duration?: number, public audioFiles?: IAudioFile[]) {}
}
