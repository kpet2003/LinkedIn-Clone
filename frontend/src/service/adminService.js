import axios from "axios";

class AdminService {

    getUsers() {
        const API_URL = "/AdminPage/"
        
        axios.get(API_URL).then (
            (response) => {
                return response.data;
            }
        )
    }
}
export default new AdminService();