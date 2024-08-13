import React, { useState, useEffect } from 'react';
import NavigationBar from './HomePage.js'
import notificationService from "../service/notificationService.js";



function Requests() {

    const [users, setUsers] = useState([]);

    useEffect(() => {
        const fetchRequests = async() => {
            try {
                const token = localStorage.getItem('jwt_token');
                const response = await  notificationService.getRequests(token);
                setUsers(response);
                console.log(response);
            } 
            catch (error) {
                console.error("There was an error getting the request list", error);
            }
        };
        fetchRequests();
    }, []);



    

}

function Notifications(){
    return(
        <div>
            <NavigationBar></NavigationBar>
            <Requests></Requests>
        </div>
    );
}

export default Notifications;