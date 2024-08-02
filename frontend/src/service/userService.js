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

    async getUserEmail(id) {
        console.log(id);
        const response = await axios.get(`/NewEmail/?id=${id}`,{
            responseType: 'json'
        } );
        const email = response.data.email;
        return email;
    }
}

export default new UserService();