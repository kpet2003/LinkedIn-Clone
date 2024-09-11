import axios from "axios";
const SERVER_URL =  "https://localhost:8080"
class AdminService {

    getUsers() {
        const API_URL = SERVER_URL + "/AdminPage/";
        return axios.get(API_URL).then(response => response.data);
    }
}
export default new AdminService();