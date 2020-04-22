import axios, {AxiosRequestConfig, AxiosResponse} from 'axios';

export abstract class AbstractRestResource<T> {

    private readonly ENDPOINT_URL: string;

    protected constructor(endpointUrl: string) {
        this.ENDPOINT_URL = endpointUrl;
    }

    public insert(newResource: T): Promise<AxiosResponse<T>> {
        newResource = this.prepareForPost(newResource);
        return axios.post(this.ENDPOINT_URL, newResource);
    }

    public fetchAll(): Promise<AxiosResponse<T[]>> {
        return axios.get(this.ENDPOINT_URL);
    }

    public update(changedResource: T): Promise<AxiosResponse<T>> {
        return axios.put(this.ENDPOINT_URL, changedResource);
    }

    public delete(resourceIdToDelete?: number) {
        // should cover null and undefined
        if (resourceIdToDelete != null) {
            return axios.delete(this.ENDPOINT_URL + `/${resourceIdToDelete}`);
        }
    }

    public abstract prepareForPost(newAudioLibrary:T):T;
}
