import React, { useState, useEffect } from 'react';
import NavigationBar from './HomePage.js'
import notificationService from "../service/notificationService.js";
import "../Notifications.css"



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


    return (
        <div>
            <h2> Your requests: </h2> 
            <ul className='list'>
                {users.map(user => (
                    <li key={user.id} className='user' >
                        <img src={`data:image/jpeg;base64,${user.profilePicture}`} alt='profile' className='profile_photo' />
                       <p>{user.firstName} {user.lastName} wants to connect with you</p> <a href={`/Profile/${user.id}`} className='profile'>Visit Profile</a> <input type='button'value={'Accept'} className='accept_button'/> 
                         <input type='button' value={'Decline'} className='decline_button'/> 
                    </li>
                ))}
            </ul>
        </div>
    );



    

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