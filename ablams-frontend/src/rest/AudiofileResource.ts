import {AxiosResponse} from 'axios';
import {AudiobookDTO} from 'ablams-js-dto/src/domain/models';
import {AbstractRestResource} from '@/rest/AbstractRestResource';

export default new class AudioBookResource extends AbstractRestResource<AudiobookDTO> {

    constructor() {
        super('/api/audio-books/{aId}/audio-files');
    }

    public prepareForPost(abook: AudiobookDTO): AudiobookDTO {
        // set id to undefined because a post request with an id will not be accepted by the backend
        abook.id = undefined;
        return abook;
    }
};
