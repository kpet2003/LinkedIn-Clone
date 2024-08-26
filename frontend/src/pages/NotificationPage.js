import React, { useState, useEffect } from 'react';
import NavigationBar from './NavigationBar.js';
import notificationService from "../service/notificationService.js";
import networkService  from '../service/networkService.js';
import "../styling/Notifications.css"
import avatar from "../icons/avatar.png"


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

function PostNotifications() {
    const [Notifications,setNotifications] = useState([])

    useEffect(() => {
        const fetchNotifications = async() => {
            try {
                const token = localStorage.getItem('jwt_token');
                const response = await  notificationService.getNotifications(token);
                setNotifications(response);
                console.log(response);
            } 
            catch (error) {
                console.error("There was an error getting the notification list", error);
            }
        };
        fetchNotifications();
    }, []);

    const gotoProfile = (user_id) => {
        console.log(user_id);
        const link = document.createElement('a');
        link.href = `/VisitProfile/${user_id}`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

    return(
        <div>
            <h2 className='my-h2'> Your Post Notifications: </h2> 
                <ul className='list'>
                    {Notifications.map(Notification => (
                        <li key={Notifications.id} className='notification' >
                            
                            { Notification.sender.profilePicture ? (<img src={`data:image/jpeg;base64,${Notification.sender.profilePicture}`} alt='profile' className='profile_photo' />)
                             :( <img src={`data:image/jpeg;base64,${avatar}`} alt='profile' className='profile_photo' />)}  
                            <p className='notification_username' onClick={() => gotoProfile(Notification.sender.id)}>{Notification.sender.firstName} {Notification.sender.lastName} </p>
                            {Notification.isComment && (<p> commented {Notification.message} on your article: {Notification.article.title} </p>)}
                            {!Notification.isComment && (<p> liked your article: {Notification.article.title} </p>)}
                        </li>
                    ))}
                </ul>
        </div>
    );


}


function Notifications(){
    return(
        <div className='notifications'>
            <NavigationBar></NavigationBar>
            <Requests></Requests>
            <PostNotifications></PostNotifications>
        </div>
    );
}

export default Notifications;