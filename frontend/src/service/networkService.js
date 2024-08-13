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

    newConnection(user_id,token) {
        const API_URL = "/Notifications/newConnection";
        return axios.post(API_URL, {
            user_id: user_id,
            token: token
        }, {
            headers: {
                'Content-Type': 'application/json'  
            }
        });
    }

    declineRequest(user_id,token) {
        const API_URL = "/Notifications/declineRequest";
        return axios.post(API_URL, {
            user_id: user_id,
            token: token
        }, {
            headers: {
                'Content-Type': 'application/json'  
            }
        });
    }
}

export default new networkService();