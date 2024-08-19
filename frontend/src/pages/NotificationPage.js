import React, { useState, useEffect } from 'react';
import NavigationBar from './HomePage.js'
import notificationService from "../service/notificationService.js";
import networkService  from '../service/networkService.js';
import "../styling/Notifications.css"



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

    const addConnection = async (userID) => {
        const token = localStorage.getItem('jwt_token');
        try {
            const response =  await networkService.newConnection(userID,token);
            console.log(response);
            setUsers(prevUsers => prevUsers.filter(user => user.id !== userID));
        }
        catch(error) {
            console.log("Error creating request",error);
        }
    }

    const decline = async (userID) => {
        const token = localStorage.getItem('jwt_token');
        try {
            const response =  await networkService.declineRequest(userID,token);
            console.log(response);
            setUsers(prevUsers => prevUsers.filter(user => user.id !== userID));
            
        }
        catch(error) {
            console.log("Error creating request",error);
        }
    }


    return (
        <div>
            <h2 className='my-h2'> Your requests: </h2> 
            <ul className='list'>
                {users.map(user => (
                    <li key={user.id} className='user' >
                        <img src={`data:image/jpeg;base64,${user.profilePicture}`} alt='profile' className='profile_photo' />
                       <p>{user.firstName} {user.lastName} wants to connect with you</p> <a href={`/VisitProfile/${user.id}`} className='profile'>Visit Profile</a> <input type='button'value={'Accept'} className='accept_button'  onClick={() => addConnection(user.id)}  /> 
                         <input type='button' value={'Decline'} className='decline_button' onClick={() => decline(user.id)}/> 
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