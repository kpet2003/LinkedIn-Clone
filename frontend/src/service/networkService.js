import axios from "axios";

class networkService {
    newRequest(user_id, sender_token) {
        const API_URL = "/Network/newRequest";
        return axios.post(API_URL, {
            user_id: user_id,
            token: sender_token
        }, {
            headers: {
                'Content-Type': 'application/json'  
            }
        });
    }
}

export default new networkService();