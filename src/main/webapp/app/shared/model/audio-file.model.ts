export const enum FileType {
    MP3 = 'MP3',
    FLAC = 'FLAC',
    M4B = 'M4B'
}

export interface IAudioFile {
    id?: number;
    fileType?: FileType;
    filePath?: string;
    audioBookId?: number;
    progressId?: number;
}

export class AudioFile implements IAudioFile {
    constructor(
        public id?: number,
        public fileType?: FileType,
        public filePath?: string,
        public audioBookId?: number,
        public progressId?: number
    ) {}
}
