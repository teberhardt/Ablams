/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.34.976 on 2022-02-13 13:54:34.

export class AudioLibraryDTO implements Serializable {
    id?: number;
    filepath: string;

    constructor(data: AudioLibraryDTO) {
        this.id = data.id;
        this.filepath = data.filepath;
    }
}

export class AudioSeriesDTO implements Serializable {
    id: number;
    seriesName: string;
    authorId: number;

    constructor(data: AudioSeriesDTO) {
        this.id = data.id;
        this.seriesName = data.seriesName;
        this.authorId = data.authorId;
    }
}

export class AudiobookDTO implements Serializable {
    id?: number;
    name: string;
    language: string;
    filePath: string;
    audioLibraryId: number;
    seriesId: number;
    authorId: number;

    constructor(data: AudiobookDTO) {
        this.id = data.id;
        this.name = data.name;
        this.language = data.language;
        this.filePath = data.filePath;
        this.audioLibraryId = data.audioLibraryId;
        this.seriesId = data.seriesId;
        this.authorId = data.authorId;
    }
}

export class AudiofileDTO implements Serializable {
    id: number;
    fileType: string;
    filePath: string;
    audiobookId: number;

    constructor(data: AudiofileDTO) {
        this.id = data.id;
        this.fileType = data.fileType;
        this.filePath = data.filePath;
        this.audiobookId = data.audiobookId;
    }
}

export class AuthorDTO implements Serializable {
    id: number;
    name: string;

    constructor(data: AuthorDTO) {
        this.id = data.id;
        this.name = data.name;
    }
}

export class CoverDTO implements Serializable {
    id: number;
    filePath: string;
    width: number;
    height: number;
    bitdepth: number;
    audiobookId: number;
    authorId: number;

    constructor(data: CoverDTO) {
        this.id = data.id;
        this.filePath = data.filePath;
        this.width = data.width;
        this.height = data.height;
        this.bitdepth = data.bitdepth;
        this.audiobookId = data.audiobookId;
        this.authorId = data.authorId;
    }
}

export class ProgressableDTO implements Serializable {
    id: number;
    userId: number;
    audiobookId: number;
    trackNr: number;
    secondsInto: number;

    constructor(data: ProgressableDTO) {
        this.id = data.id;
        this.userId = data.userId;
        this.audiobookId = data.audiobookId;
        this.trackNr = data.trackNr;
        this.secondsInto = data.secondsInto;
    }
}

export interface Serializable {
}
