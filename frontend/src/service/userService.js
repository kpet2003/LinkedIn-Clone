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

    async getUserEmail(ID) {
        if (!ID) {
            console.error("Invalid ID");
            return;
        }
        console.log(ID);
        const url = "/NewEmail";
        const response = await axios.get(url,{
            params: {id : ID},
            responseType: 'json'
        }).then( function (response){
                console.log('got response ', response);
            }
        );
        
        const email = response.data.email;
        return email;
    }
    
}

export default new UserService();