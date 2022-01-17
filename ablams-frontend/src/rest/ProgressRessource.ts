import {ProgressableDTO} from 'ablams-communication/ablams/communication';
import {AbstractRestResource} from '@/rest/AbstractRestResource';
import axios, {AxiosResponse} from "axios";

class ProgressResource extends AbstractRestResource<ProgressableDTO> {

    constructor() {
        super('/api/progressables');
    }

    prepareForPost(progressableDTO: ProgressableDTO): ProgressableDTO {
        return progressableDTO;
    }

    public fetchByAudioBookId(id: number): Promise<AxiosResponse<ProgressableDTO>> {
        return axios.post(this.ENDPOINT_URL + `/${id}/start`);
    }
}
export default new ProgressResource();
