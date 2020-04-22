import {AudioLibraryDTO} from 'ablams-js-dto/src/domain/models';
import {AbstractRestResource} from '@/rest/AbstractRestResource';

export default new class AudioLibraryResource extends AbstractRestResource<AudioLibraryDTO> {

    constructor() {
        super('/api/audio-libraries');
    }

    public prepareForPost(aLib: AudioLibraryDTO): AudioLibraryDTO {
        // set id to undefined because a post request with an id will not be accepted by the backend
        aLib.id = undefined;
        return aLib;
    }
};
