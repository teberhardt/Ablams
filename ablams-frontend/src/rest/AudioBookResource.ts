import axios, {AxiosResponse} from 'axios';
import {AudiobookDTO} from 'ablams-communication/ablams/communication';
import {AbstractRestResource} from '@/rest/AbstractRestResource';

export default new class AudioBookResource extends AbstractRestResource<AudiobookDTO> {

    constructor() {
        super('/api/audio-books');
    }

    public prepareForPost(abook: AudiobookDTO): AudiobookDTO {
        // set id to undefined because a post request with an id will not be accepted by the backend
        abook.id = undefined;
        return abook;
    }

    public fetchById(aId: Number): Promise<AxiosResponse<AudiobookDTO>> {
        return axios.get(this.ENDPOINT_URL + `/${aId}`);
    }

};
