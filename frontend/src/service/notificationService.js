import axios from "axios";
const SERVER_URL =  "http://localhost:8080"

class notificationService {
    getRequests(token) {
        const API_URL = SERVER_URL +`/Notifications/Requests?token=${encodeURIComponent(token)}`;
        return axios.get(API_URL).then(response => response.data);
    }

    getNotifications(token) {
        const API_URL = SERVER_URL +`/Notifications/PostNotifications?token=${encodeURIComponent(token)}`;
        return axios.get(API_URL).then(response => response.data);
    }

}

export default new notificationService();