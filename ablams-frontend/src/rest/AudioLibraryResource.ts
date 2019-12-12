import axios, {AxiosRequestConfig, AxiosResponse} from 'axios';
import {AudioLibraryDTO} from 'ablams-js-dto/src/domain/models';

export default new class AudioLibraryResource {

    private readonly URL_ENDPOINT: string  = '/api/audio-libraries';

    public fetchAll(): Promise<AxiosResponse<AudioLibraryDTO[]>> {
        return axios.get(this.URL_ENDPOINT);
    }

    public insert(aLib: AudioLibraryDTO): void {
        // @ts-ignore
        aLib.id = null;
        axios.post(this.URL_ENDPOINT, aLib).then((r) => console.log('request result: ' + r) );
    }

};
