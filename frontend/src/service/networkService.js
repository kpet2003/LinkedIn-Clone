import axios from "axios";
const SERVER_URL =  "https://localhost:8080"
class networkService {

    // get a list of all users
    getUsers(token,cancel) {
        const API_URL = SERVER_URL +"/Network/getUsers";
        return axios.get(API_URL,{params:{token:token},signal:cancel.signal}).then(response => response.data);
    }

    // make a new connection request
    newRequest(user_id, sender_token) {
        const API_URL = SERVER_URL +"/Network/newRequest";
        return axios.post(API_URL, {
            user_id: user_id,
            token: sender_token
        }, {
            headers: {
                'Content-Type': 'application/json'  
            }
        });
    }

    // accept a connection request
    newConnection(user_id,token) {
        const API_URL = SERVER_URL +"/Notifications/newConnection";
        return axios.post(API_URL, {
            user_id: user_id,
            token: token
        }, {
            headers: {
                'Content-Type': 'application/json'  
            }
        });
    }

    // reject a connection request
    declineRequest(user_id,token) {
        const API_URL = SERVER_URL +"/Notifications/declineRequest";
        return axios.post(API_URL, {
            user_id: user_id,
            token: token
        }, {
            headers: {
                'Content-Type': 'application/json'  
            }
        });
    }

    // fetch the users to whom the user has made a connection request
    fetchRequests(token,cancel) {
        const API_URL = SERVER_URL +`/Network/Requests?token=${encodeURIComponent(token)}`;
        return axios.get(API_URL,{signal:cancel.signal}).then(response => response.data);
    }

    // fetch connections of user
    fetchConnections(token,cancel) {
        const API_URL = SERVER_URL +`/Network/Connections?token=${encodeURIComponent(token)}`;
        return axios.get(API_URL,{signal:cancel.signal}).then(response => response.data);
    }

    // fetch connections of user with id=id
    fetchConnectionsById(token,id,cancel) {
        const API_URL = SERVER_URL + `/ViewNetwork/getConnections/${id}`;
        return axios.get(API_URL,{params:{token:token},signal:cancel.signal}).then(response => response.data);
    }
}

const network_service = new networkService();
export default network_service;