import axios from "axios";

class AdminService {

    getUsers() {
        const API_URL = "/AdminPage/"
        return axios.get(API_URL).then(response => response.data);
    }
}
export default new AdminService();