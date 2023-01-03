import axios, {AxiosResponse} from 'axios';
import AuthUser from '@/auth/AuthUser';
const API_URL = 'http://localhost:8080/api/user/';

const LOCALSTORAGE_ID = 'user';

class AuthService {
    public login(user: AuthUser): Promise<AxiosResponse<any>> {
        return axios
            .post(API_URL + 'login', {}, {
                auth: {
                    username: user.username,
                    password: user.password,
                }})
            .then(response => {
                localStorage.setItem(LOCALSTORAGE_ID, JSON.stringify(user));
                return response;
            });
    }
    public isLoggedIn(): boolean {
        const item = localStorage.getItem(LOCALSTORAGE_ID);
        if(item == undefined || item == null) {
            return false;
        }
        let authUser = JSON.parse(item) as AuthUser;
        if (authUser.username != undefined && authUser.password != undefined) {
            return true; //iuohnäöoiuhäoijhoi
        } else {
            return false;
        }
    }
    public logout() {
        localStorage.removeItem(LOCALSTORAGE_ID);
    }
}

export default new AuthService();
