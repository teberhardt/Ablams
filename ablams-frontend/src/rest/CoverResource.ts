import {AbstractRestResource} from '@/rest/AbstractRestResource';
import {CoverDTO} from 'ablams-models/ablams/communication';

export default new class CoverResourceResource extends AbstractRestResource<CoverDTO> {

    constructor() {
        super('/api/cover');
    }

    public prepareForPost(cover: CoverDTO): CoverDTO {

        return cover;
    }
};
