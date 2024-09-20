import axios from "axios";
const SERVER_URL =  "https://localhost:8080"
class AdminService {

    // fetch a list containing all user data; the list is in json format
    getUsers(cancel) {
        const API_URL = SERVER_URL + "/AdminPage/";
        return axios.get(API_URL,{signal:cancel.signal}).then(response => response.data);
    }

    // fetch user data of the users with ids in the user_ids list; the data is returned in xml format
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

const adminService = new AdminService();
export default adminService;