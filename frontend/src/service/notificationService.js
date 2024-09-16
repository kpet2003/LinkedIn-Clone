import axios from "axios";
const SERVER_URL =  "https://localhost:8080"

class notificationService {
    getRequests(token,cancel) {
        const API_URL = SERVER_URL +`/Notifications/Requests?token=${encodeURIComponent(token)}`;
        return axios.get(API_URL,{signal:cancel.signal}).then(response => response.data);
    }

    getNotifications(token,cancel) {
        const API_URL = SERVER_URL +`/Notifications/PostNotifications?token=${encodeURIComponent(token)}`;
        return axios.get(API_URL,{signal:cancel.signal}).then(response => response.data);
    }

}

export default new notificationService();