import axios from "axios";
const API_URL = "http://localhost:8080/SignUp/signup";

class UserService {
    saveUser(user) {
        return axios.post(API_URL, user, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }
}
export default new UserService();