import '../styling/Network.css';
import React, { useState, useEffect} from 'react';
import networkService from '../service/networkService.js'; 
import UserService from '../service/userService.js';
import { useParams } from 'react-router-dom';
import NavigationBar from './NavigationBar.js';
import placeholder from '../icons/avatar.png';



function Network() {

    const [connectedUsers,setConnectedUsers] = useState([]);
    const { id }  = useParams();
    const [isAdmin,setAdmin] = useState(false);
    
    useEffect(() => {
        const token =  UserService.decodeToken(localStorage.getItem('jwt_token'));

        // check whether the viewer is the admin
        if(token.sub === 'admin@gmail.com') {
            setAdmin(true);
        }

        const cancelConnections = new AbortController();

        // fetch the user's connections
        const getConnections = async () => {
            try {
                console.log(id);
                const response = await networkService.fetchConnectionsById(localStorage.getItem('jwt_token'),id,cancelConnections);
                const finalUsers = response;
                setConnectedUsers(finalUsers);
                console.log(finalUsers);
            } 
            
            catch (error) {
                console.error('Error fetching user network:', error);
            }
        };
        getConnections();

        return () => {
            cancelConnections.abort(); 
        };
    }, [id]);


    return(
        <div >
            {/* display navigation bar only if  the user viewing the page is not the admin */}
            {!isAdmin && (<NavigationBar></NavigationBar>)}
            <div className='net'>

            {/* display the user's connections in grid format */}
            {connectedUsers.map(connectedUser => (
                    <span key={connectedUser.id} className='ConnectedUser' >
                         <img src={connectedUser.profilePicture?`data:image/jpeg;base64,${connectedUser.profilePicture}`:placeholder } alt = 'profile'className='picture'/>
                       <p className='title'>{connectedUser.firstName} {connectedUser.lastName} </p>

                       {connectedUser.workTitle && connectedUser.workplace &&  (<p className='description'>{connectedUser.workTitle} at {connectedUser.workplace}</p> )}
                       {connectedUser.workTitle && !connectedUser.workplace && ( <p className='description'>{connectedUser.workTitle} </p> )}
                       {!connectedUser.workTitle && !connectedUser.workplace && ( <p className='description'>{connectedUser.email}</p>) }
                
                       
                       <a  href={`/VisitProfile/${connectedUser.id}`} className='profile_link'>Visit Profile</a> 
                     
                    </span>
                ))}
            </div>
        
        </div>
    );
}

function ViewNetwork() {
    return(
        <div>
            <Network></Network>
        </div>
    );
}



export default ViewNetwork;