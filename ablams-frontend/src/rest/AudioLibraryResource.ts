import {AxiosResponse} from 'axios';
import {AudioLibraryDTO} from 'ablams-js-dto/src/domain/models';
import {AbstractRestResource} from '@/rest/AbstractRestResource';

export default new class AudioLibraryResource extends AbstractRestResource<AudioLibraryDTO> {

    private static prepareForPost(aLib: AudioLibraryDTO): AudioLibraryDTO {
        // set id to undefined because a post request with an id will not be accepted by the backend
        aLib.id = undefined;
        return aLib;
    }

    constructor() {
        super('/api/audio-libraries');
    }

    public insert(newAudioLibrary: AudioLibraryDTO): Promise<AxiosResponse<AudioLibraryDTO>> {
       newAudioLibrary = AudioLibraryResource.prepareForPost(newAudioLibrary);
       return super.insert(newAudioLibrary);
    }
};
