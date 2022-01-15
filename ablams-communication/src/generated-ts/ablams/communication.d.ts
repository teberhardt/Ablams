/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.34.976 on 2022-01-16 00:14:40.

export interface AudioLibraryDTO extends Serializable {
    id?: number;
    filepath: string;
}

export interface AudioSeriesDTO extends Serializable {
    id: number;
    seriesName: string;
    authorId: number;
}

export interface AudiobookDTO extends Serializable {
    id?: number;
    name: string;
    language: string;
    filePath: string;
    audioLibraryId: number;
    seriesId: number;
    authorId: number;
}

export interface AudiofileDTO extends Serializable {
    id: number;
    fileType: string;
    filePath: string;
    audiobookId: number;
}

export interface AuthorDTO extends Serializable {
    id: number;
    name: string;
}

export interface CoverDTO extends Serializable {
    id: number;
    filePath: string;
    width: number;
    height: number;
    bitdepth: number;
    audiobookId: number;
    authorId: number;
}

export interface ProgressableDTO extends Serializable {
    id: number;
    userId: number;
    abookId: number;
    afileId: number;
    minutesInto: number;
}

export interface Serializable {
}
