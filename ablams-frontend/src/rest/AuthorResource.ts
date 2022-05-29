
import {AbstractRestResource} from '@/rest/AbstractRestResource';
import {AuthorDTO} from 'ablams-models/ablams/communication';
import axios, {AxiosResponse} from 'axios';

class AuthorResource extends AbstractRestResource<AuthorDTO> {

    constructor() {
        super('/api/authors');
    }

    public getById(authorId: number): Promise<AxiosResponse<AuthorDTO>>{
        return axios.get(`/authors/${authorId}`);
    }

    public prepareForPost(newAudioLibrary: AuthorDTO): AuthorDTO {
        throw new Error('Not allowed');
    }
}
export default new AuthorResource();
