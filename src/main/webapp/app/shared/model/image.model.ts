export interface IImage {
    id?: number;
    filePath?: string;
    width?: number;
    height?: number;
    bitdepth?: number;
    audioBookId?: number;
    authorId?: number;
}

export class Image implements IImage {
    constructor(
        public id?: number,
        public filePath?: string,
        public width?: number,
        public height?: number,
        public bitdepth?: number,
        public audioBookId?: number,
        public authorId?: number
    ) {}
}
