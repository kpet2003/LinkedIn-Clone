import '../styling/Network.css';
import React, { useState, useEffect, useRef } from 'react';
import networkService from '../service/networkService.js'; 
import UserService from '../service/userService.js';
import { useParams } from 'react-router-dom';


function Network() {

    const [connectedUsers,setConnectedUsers] = useState([]);
    const { id }  = useParams();

    useEffect(() => {
        const getConnections = async () => {
            try {
                console.log(id);
                const response = await networkService.fetchConnectionsById(id);
                const finalUsers = response;
                setConnectedUsers(finalUsers);
                console.log(finalUsers);
            } 
            
            catch (error) {
                console.error('Error fetching user network:', error);
            }
        };
        getConnections();
    }, [id]);


    return(
        <div >
            <div className='net'>
            {connectedUsers.map(connectedUser => (
                    <span key={connectedUser.id} className='ConnectedUser' >
                        <img src={`data:image/jpeg;base64,${connectedUser.profilePicture}`} alt='profile' className='picture' />
                       <p className='title'>{connectedUser.firstName} {connectedUser.lastName} </p> <p className='description'>{connectedUser.workTitle} at </p> <a  href={`/VisitProfile/${connectedUser.id}`} className='profile_link'>Visit Profile</a> 
                     
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