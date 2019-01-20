export interface IImage {
    id?: number;
    filePath?: string;
    audioBookId?: number;
    authorId?: number;
}

export class Image implements IImage {
    constructor(public id?: number, public filePath?: string, public audioBookId?: number, public authorId?: number) {}
}
