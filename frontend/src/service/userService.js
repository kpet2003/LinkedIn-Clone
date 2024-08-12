import axios from "axios";
import { jwtDecode } from "jwt-decode";

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
        return axios.post(API_URL, user);
    }

    decodeToken(token) {
        const decoded = jwtDecode(token);
        console.log(decoded);
        return decoded;

    }

    changeEmail(data) {
        const API_URL = "/NewEmail";
        return axios.put(API_URL, data);
    }

    changePassword(password) {
        const API_URL = "/NewPassword";
        return axios.post(API_URL, password).data;
    }

    changeProfilePicture(picture) {
        const API_URL = "/Profile/pfpchange";
        return axios.post(API_URL, picture, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).data;
    }

    changeEducation(education) {
        const API_URL = "/Profile/educhange";
        return axios.post(API_URL, education, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).data;
    }

    changeWork(work) {
        const API_URL = "/Profile/workchange";
        return axios.post(API_URL, work, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).data;
    }

    changeSkills(skills) {
        const API_URL = "/Profile/skillchange";
        return axios.post(API_URL, skills, {
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