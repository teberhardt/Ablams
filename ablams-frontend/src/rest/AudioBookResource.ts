import {AxiosResponse} from 'axios';
import {AudioBookDTO} from 'ablams-js-dto/src/domain/models';
import {AbstractRestResource} from '@/rest/AbstractRestResource';

export default new class AudioBookResource extends AbstractRestResource<AudioBookDTO> {

    constructor() {
        super('/api/audio-books');
    }

    public prepareForPost(abook: AudioBookDTO): AudioBookDTO {
        // set id to undefined because a post request with an id will not be accepted by the backend
        abook.id = undefined;
        return abook;
    }
};
