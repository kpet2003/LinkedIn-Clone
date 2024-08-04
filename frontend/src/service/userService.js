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

    changeEmail(email) {
        const API_URL = "/NewEmail";
        return axios.post(API_URL, email, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).data;
    }

    changePassword(password) {
        const API_URL = "/NewPassword";
        return axios.post(API_URL, password, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).data;
    }

    async getUserEmail(ID) {
        if (!ID) {
            console.error("Invalid ID");
            return;
        }
        const url = "/NewEmail";
        const response = await axios.get(url,{
            params: {id : ID},
            responseType: 'json'
        });
    
        const email = response.data.email;
        return email;
    }

    async getUserData(ID) {
        if (!ID) {
            console.error("Invalid ID");
            return;
        }
        const url = "/Profile";
        const response = await axios.get(url,{
            params: {id : ID},
            responseType: 'json'
        });
        console.log(response.data);
        return response.data;
    }
    
}

export default new UserService();