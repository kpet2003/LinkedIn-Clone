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
        return axios.put(API_URL, password).data;
    }

    changeProfilePicture(picture) {
        const API_URL = "/Profile/pfpchange";
        return axios.put(API_URL, picture, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).data;
    }

    changeEducation(education) {
        const API_URL = "/Profile/educhange";
        return axios.put(API_URL, education, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).data;
    }

    changeWork(work) {
        const API_URL = "/Profile/workchange";
        return axios.put(API_URL, work, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).data;
    }

    changeSkills(skills) {
        const API_URL = "/Profile/skillchange";
        return axios.put(API_URL, skills, {
            headers: {
                 'Content-Type': 'application/json'
            }
        }).data;
    }

    changeEduState(state){
        const API_URL = "/Profile/edubool";
        return axios.put(API_URL,state);
    }

    changeWorkState(state){
        const API_URL = "/Profile/workbool";
        return axios.put(API_URL,state);
    }

    changeSkillsState(state){
        const API_URL= "/Profile/skillsbool";
        return axios.put(API_URL,state);
    }

    changeWorkTitle(title){
        const API_URL= "/Profile/worktitle"
        return axios.put(API_URL,title);
    }

    changeWorkplace(place){
        const API_URL= "/Profile/workplace"
        return axios.put(API_URL,place);
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

    async getUserData(token) {
        if (!token) {
            console.error("Invalid token");
            return;
        }
        const url = "/Profile";
        const response = await axios.get(url,{
            params: {token: token},
            responseType: 'json'
        });
        console.log(response.data);
        return response.data;
    }

    async getProfile(id){
        const url = `/VisitProfile/${id}`;
        const response = await axios.get(url, {
            responseType: 'json'
        });
        console.log(response.data);
        return response.data;
    }

    async getUserChatData(token){
        const url = '/Messages';
        const response = await axios.get(url, {
            params: {token: token},
            responseType: 'json'
        });
        return response.data;
    }

    async getChatHistory(user1,user2){
        const url = `/chathistory/${user1}/${user2}`
        const response = await axios.get(url);
        return response.data;
    }

    async getImage(user){
        const url = `/image/${user}`;
        const response = await axios.get(url);
        return response.data;
    }
    
}

export default new UserService();