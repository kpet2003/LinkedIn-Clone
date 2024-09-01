import React, { useState, useEffect } from 'react';
import NavigationBar from './NavigationBar.js';
import notificationService from "../service/notificationService.js";
import networkService  from '../service/networkService.js';
import "../styling/Notifications.css"
import placeholder from '../icons/avatar.png';


function Requests({users,setUsers}) {

   


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
            {  users.length>0 && (
                <div>
                <h2 className='my-h2'> Your requests: </h2> 
                    <ul className='list'>
                         { users.map(user => (
                            <li key={user.id} className='user' >
                                <img src={user.profilePicture?`data:image/jpeg;base64,${user.profilePicture}`:placeholder} alt='profile' className='profile_photo' />
                               <p>{user.firstName} {user.lastName} wants to connect with you</p> <a href={`/VisitProfile/${user.id}`} className='profile'>Visit Profile</a> <input type='button'value={'Accept'} className='accept_button'  onClick={() => addConnection(user.id)}  /> 
                                 <input type='button' value={'Decline'} className='decline_button' onClick={() => decline(user.id)}/> 
                            </li>
                        ))}
                    </ul>
                </div>
            )
            }
            
        </div>
    );




}

function PostNotifications({Notifications}) {
    



    const gotoProfile = (user_id) => {
        console.log(user_id);
        const link = document.createElement('a');
        link.href = `/VisitProfile/${user_id}`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

    return(
        <div>{Notifications.length>0 && (
            <div>
                <h2 className='my-h2'> Your Post Notifications: </h2> 
                <ul className='list'>
                    {Notifications.map(Notification => (
                        <li key={Notifications.id} className='notification' >
                             <img src={Notification.sender.profilePicture?`data:image/jpeg;base64,${Notification.sender.profilePicture}`:placeholder} alt='profile' className='profile_photo' /> 
                            <p className='notification_username' onClick={() => gotoProfile(Notification.sender.id)}>{Notification.sender.firstName} {Notification.sender.lastName} </p>
                            {Notification.isComment && (<p> commented {Notification.message} on your article: {Notification.article.title} </p>)}
                            {!Notification.isComment && (<p> liked your article: {Notification.article.title} </p>)}
                        </li>
                    ))}
                </ul>
            </div>    
        )}
   
          
        </div>
    );


}


function Notifications(){
    const [users, setUsers] = useState([]);
    const [Notifications,setNotifications] = useState([]);
    const token = localStorage.getItem('jwt_token');
   
    
    useEffect(() => {
       

        const fetchData = async () => {
            try {
                const [users, Notifications] = await Promise.all([
                    notificationService.getRequests(token),
                    notificationService.getNotifications(token)
                ]);
                setUsers(users);
                setNotifications(Notifications);
            } 
            catch (error) {
                console.error("There was an error getting the user list", error);
            }
        }
        fetchData();
    },[]);


    
    return(
        <div className='notifications'>
            <NavigationBar></NavigationBar>
            <Requests users = {users}  setUsers={setUsers}/>
            <PostNotifications Notifications = {Notifications}></PostNotifications>
        </div>
    );
}

export default Notifications;