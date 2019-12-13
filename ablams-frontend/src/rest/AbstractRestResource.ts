import axios, {AxiosResponse} from 'axios';

export abstract class AbstractRestResource<T> {

    private readonly ENDPOINT_URL: string;

    protected constructor(endpointUrl: string) {
        this.ENDPOINT_URL = endpointUrl;
    }

    public insert(newResource: T): Promise<AxiosResponse<T>> {
        return axios.post(this.ENDPOINT_URL, newResource);
    }

    public fetchAll(): Promise<AxiosResponse<T[]>> {
        return axios.get(this.ENDPOINT_URL);
    }
}
