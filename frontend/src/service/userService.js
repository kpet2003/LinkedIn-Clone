import axios from "axios";
import { jwtDecode } from "jwt-decode";
const SERVER_URL =  "https://localhost:8080"


class UserService {
    saveUser(user) {
        const API_URL = SERVER_URL +"/SignUp/signup";
        return axios.post(API_URL, user, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }
    
    loginUser(user) {
        const API_URL = SERVER_URL +"/Login";
        return axios.post(API_URL, user);
    }

    decodeToken(token) {
        const decoded = jwtDecode(token);
        console.log(decoded);
        return decoded;

    }

    checkConnection(token, id) {
        const API_URL = SERVER_URL +"/ViewProfile";
        return axios.get(API_URL, {
            params: {
                user_id: id,
                token:token
            }
        }).then(response => response.data);
    }
    

    changeEmail(data) {
        const API_URL = SERVER_URL +"/NewEmail";
        return axios.put(API_URL, data);
    }

    changePassword(password) {
        const API_URL = SERVER_URL +"/NewPassword";
        return axios.put(API_URL, password).data;
    }

    changeProfilePicture(picture) {
        const API_URL = SERVER_URL +"/Profile/pfpchange";
        return axios.put(API_URL, picture, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).data;
    }

    changeEducation(education) {
        const API_URL = SERVER_URL + "/Profile/educhange";
        console.log('education is: ', education);
               return axios.put(API_URL, education, {
            headers: {
                 'Content-Type': 'application/json'
            }
        }).data;
    }
    
    
    getEducation(token) {
        const API_URL = SERVER_URL + "/Profile/GetEducation";
        return axios.get(API_URL,{
            params: {token : token},
            responseType: 'json'
        }).then(response => response.data);
    }

    getEducationById(id) {
        const API_URL = SERVER_URL + `/VisitProfile/getEducation/${id}`;
        return axios.get(API_URL, {responseType: 'json' }).then(response => response.data);
    }

    removeEdu(education_id,token) {
        const API_URL = SERVER_URL + "/Profile/DeleteEdu";
        return axios.delete(API_URL, {params: {education_id: education_id,token: token}});
    }

    changeWork(work) {
        const API_URL = SERVER_URL + "/Profile/workchange";
        return axios.put(API_URL, work, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).data;
    }

    getExperience(token) {
        const API_URL = SERVER_URL + "/Profile/GetExperience";
        return axios.get(API_URL,{
            params: {token : token},
            responseType: 'json'
        }).then(response => response.data);
    }

    getExperienceById(id) {
        const API_URL = SERVER_URL +`/VisitProfile/getExperience/${id}`;
        return axios.get(API_URL, {responseType: 'json' }).then(response => response.data);
    }

    removeExp(experience_id,token) {
        const API_URL = SERVER_URL + "/Profile/DeleteExp";
        return axios.delete(API_URL, {params: {experience_id: experience_id,token: token}});
    }

    changeSkills(skills) {
        const API_URL = SERVER_URL + "/Profile/skillchange";
        return axios.put(API_URL, skills, {
            headers: {
                 'Content-Type': 'application/json'
            }
        }).data;
    }

    getSkills(token) {
        const API_URL = SERVER_URL + "/Profile/GetSkills";
        return axios.get(API_URL,{
            params: {token : token},
            responseType: 'json'
        }).then(response => response.data);
    }

    getSkillsById(id) {
        const API_URL = SERVER_URL + `/VisitProfile/getSkills/${id}`;
        return axios.get(API_URL, {responseType: 'json' }).then(response => response.data);
    }


    removeSkill(skill_id,token) {
        const API_URL = SERVER_URL + "/Profile/DeleteSkill";
        return axios.delete(API_URL, {params: {skill_id: skill_id,token: token}});
    }

    changeEduState(state){
        const API_URL = SERVER_URL + "/Profile/edubool";
        return axios.put(API_URL,state);
    }

    changeWorkState(state){
        const API_URL = SERVER_URL + "/Profile/workbool";
        return axios.put(API_URL,state);
    }

    changeSkillsState(state){
        const API_URL= SERVER_URL + "/Profile/skillsbool";
        return axios.put(API_URL,state);
    }

    changeWorkTitle(title){
        const API_URL= SERVER_URL + "/Profile/worktitle"
        return axios.put(API_URL,title);
    }

    changeWorkplace(place){
        const API_URL= SERVER_URL + "/Profile/workplace"
        return axios.put(API_URL,place);
    }
    

    async getUserEmail(ID) {
        if (!ID) {
            console.error("Invalid ID");
            return;
        }
        const url = SERVER_URL + "/NewEmail";
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
        const url = SERVER_URL +"/Profile";
        const response = await axios.get(url,{
            params: {token: token},
            responseType: 'json'
        });
        console.log(response.data);
        return response.data;
    }

    async getProfile(id){
        const url = SERVER_URL + `/VisitProfile/${id}`;
        const response = await axios.get(url, {
            responseType: 'json'
        });
        console.log(response.data);
        return response.data;
    }

    async getUserChatData(token){
        const url = SERVER_URL + '/Messages';
        const response = await axios.get(url, {
            params: {token: token},
            responseType: 'json'
        });
        return response.data;
    }

    async getChatHistory(user1,user2){
        const url = SERVER_URL + `/chathistory/${user1}/${user2}`
        const response = await axios.get(url);
        return response.data;
    }

    async getImage(user){
        const url = SERVER_URL + `/image/${user}`;
        const response = await axios.get(url);
        return response.data;
    }
    
}

export default new UserService();