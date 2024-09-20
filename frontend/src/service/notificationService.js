import axios from "axios";
const SERVER_URL =  "https://localhost:8080"

class notificationService {
    // fetch the users who have made a connection request to the user
    getRequests(token,cancel) {
        const API_URL = SERVER_URL +`/Notifications/Requests?token=${encodeURIComponent(token)}`;
        return axios.get(API_URL,{signal:cancel.signal}).then(response => response.data);
    }

    // fetch the notifications regarding the posts that the user has written
    getNotifications(token,cancel) {
        const API_URL = SERVER_URL +`/Notifications/PostNotifications?token=${encodeURIComponent(token)}`;
        return axios.get(API_URL,{signal:cancel.signal}).then(response => response.data);
    }

}
const notification_service =  new notificationService();
export default notification_service;