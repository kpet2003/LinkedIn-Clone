import NavigationBar from './HomePage.js'
import React, { useEffect, useState } from 'react';
import AdminService from '../service/adminService.js'; 
import glass from "../glass.png";
import '../Network.css';
import networkService from '../service/networkService.js'; 
import UserService from '../service/userService.js';

function SearchBar() {

    const [users, setUsers] = useState([]);
    const [selectedUsers,setSelectedUsers] = useState("");
    const [searchTerm, setSearchTerm] = useState("");
    const [requestUsers,setRequestUsers] = useState([]);
    const [connectedUsers,setConnectedUsers] = useState([]);

    // renders the list of existing users, except for the admin 
    useEffect(() => {
        const token = localStorage.getItem('jwt_token');
        const email = UserService.decodeToken(token).sub;
     
        const fetchUsers = async() => {
            try {
                const response = await  AdminService.getUsers();
                const finalUsers = response.filter(user => user.admin !== true && 
                    user.email !== email &&
                    !connectedUsers.includes(user.id) &&
                    !requestUsers.includes(user.id))
                setUsers(finalUsers);
            } 
            catch (error) {
                console.error("There was an error getting the user list", error);
            }
        };

        const findRequests = async() => {
            const token = localStorage.getItem('jwt_token');

            try {
                const response = await networkService.fetchRequests(token);
                const finalUsers = response;
                setRequestUsers(finalUsers);
                console.log(finalUsers);
            }
            catch (error) {
                console.error("There was an error getting the request list", error);
            }

        }

        const findConnections = async() => {
            const token = localStorage.getItem('jwt_token');

            try {
                const response = await networkService.fetchConnections(token);
                const finalUsers = response;
                setConnectedUsers(finalUsers);
                console.log(finalUsers);
            }
            catch (error) {
                console.error("There was an error getting the connections list", error);
            }

        }

        findConnections();
        findRequests();
        fetchUsers();
    }, []);


    const handleFilter = (event) => {
        setSearchTerm(event.target.value);
        setSelectedUsers(searchTerm);
        
        if (searchTerm === "") {
            setSelectedUsers([]);
        } 
        else {
            const newFilter = users.filter(user => 
                (user.firstName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
                 user.lastName?.toLowerCase().includes(searchTerm.toLowerCase())) || false
            );
            setSelectedUsers(newFilter);
        }
    };

    const handleSearchClick = () => {
        handleFilter();
    };


    const makeRequest = async (userID) => {
     
        const token = localStorage.getItem('jwt_token');
        try {
            const response =  await networkService.newRequest(userID,token);
            console.log(response);
        }
        catch(error) {
            console.log("Error creating request",error);
        }
        
    }



    return (
        <div className='searchBar'>
            <input 
                type='text'  
                value={searchTerm}  
                onChange={handleFilter} 
                className='search' 
                placeholder='Search Users'
            />
            <img 
                src={glass} 
                alt='search' 
                className='photo' 
                onClick={handleSearchClick} 
            />
           
            {selectedUsers.length > 0 && (
                <ul className='list'>
                    {selectedUsers.slice(0, 15).map((value) => {
                        const isConnected = connectedUsers.some(user => user.id === value.id);
                        const isRequested = requestUsers.some(user => user.id === value.id);
    
                        return (
                            <li key={value.id} className="user">
                                <p>
                                    <img 
                                        src={`data:image/jpeg;base64,${value.profilePicture}`} 
                                        alt='profile' 
                                        className='profile_photo' 
                                    />
                                    {value.firstName} {value.lastName}
                                    <a href='/Profile'> Visit Profile </a>
    
                                    {isConnected && <p>Connected</p>}
                                    {isRequested && !isConnected && <p>Pending Request</p>}
                                    {!isConnected && !isRequested && (
                                        <input 
                                            type='button' 
                                            value='Make connection' 
                                            className='button'
                                            onClick={() => makeRequest(value.id)} 
                                        />
                                    )}
                                </p>
                            </li>
                        );
                       
                    })}
                </ul>
            )}
        
        </div>
    );
}    

function MyNetwork() {

    const [connectedUsers,setConnectedUsers] = useState([]);

    useEffect(() => {
        const findConnections = async() => {
            const token = localStorage.getItem('jwt_token');

            try {
                const response = await networkService.fetchConnections(token);
                const finalUsers = response;
                setConnectedUsers(finalUsers);
                console.log(finalUsers);
            }
            catch (error) {
                console.error("There was an error getting the connections list", error);
            }

        }

        findConnections();
    }, []);



    return(
        <div >
            <div className='net'>
            {connectedUsers.map(connectedUser => (
                    <span key={connectedUser.id} className='ConnectedUser' >
                        <img src={`data:image/jpeg;base64,${connectedUser.profilePicture}`} alt='profile' className='profile_photo' />
                       <p>{connectedUser.firstName} {connectedUser.lastName} </p> <p>{connectedUser.workExperience}</p> <a href={`/Profile/${connectedUser.id}`} className='profile'>Visit Profile</a> 
                     
                    </span>
                ))}
            </div>
        
        </div>
    );
}



function Network(){
    return(
        <div>
            <NavigationBar></NavigationBar>
            <SearchBar></SearchBar>
            <MyNetwork></MyNetwork>
        </div>
    );
}

export default Network;