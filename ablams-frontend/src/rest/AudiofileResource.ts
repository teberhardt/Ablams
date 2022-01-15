import {AudiofileDTO} from 'ablams-communication/ablams/communication';
import {AbstractRestResource} from '@/rest/AbstractRestResource';

class AudiofileResource extends AbstractRestResource<AudiofileDTO> {

    constructor() {
        super('/api/audio-books/{aId}/audio-files');
    }

    prepareForPost(newAudioLibrary: AudiofileDTO): AudiofileDTO {
        return newAudioLibrary;
    }

    public getStreamEndpointForAudioFile(afileId: Number): string{
        return `/api/audio-files/{afileId}/stream`;
    }
}
export default  new AudiofileResource();
