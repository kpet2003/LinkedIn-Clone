import axios from "axios";

class UserService {
    saveUser(user) {
        const API_URL = "/SignUp/signup";
        return axios.post(API_URL, user, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }
    
    loginUser(user) {
        const API_URL = "/Login";
        return axios.post(API_URL, user, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }
}
export default new UserService();