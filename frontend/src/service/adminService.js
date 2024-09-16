import axios from "axios";
const SERVER_URL =  "https://localhost:8080"
class AdminService {

    getUsers(cancel) {
        const API_URL = SERVER_URL + "/AdminPage/";
        return axios.get(API_URL,{signal:cancel.signal}).then(response => response.data);
    }

    getxmlUsers(user_ids) {
        const API_URL = SERVER_URL + "/AdminPage/getXml";
        const params = user_ids.map(id => `user_ids=${encodeURIComponent(id)}`).join('&');

        return axios.get(`${API_URL}?${params}`, {
            headers: {
                'Content-Type': 'application/xml' 
            }
        }).then(response => response.data);
    }

}
export default new AdminService();