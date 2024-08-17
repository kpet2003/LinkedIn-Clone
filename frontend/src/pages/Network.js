import NavigationBar from './HomePage.js'
import React, { useState, useEffect, useRef } from 'react';
import AdminService from '../service/adminService.js'; 
import glass from "../icons/glass.png";
import '../Network.css';
import networkService from '../service/networkService.js'; 
import UserService from '../service/userService.js';

function SearchBar() {

    const [users, setUsers] = useState([]);
    const [selectedUsers,setSelectedUsers] = useState("");
    const [searchTerm, setSearchTerm] = useState("");
    const [requestUsers,setRequestUsers] = useState([]);
    const [connectedUsers,setConnectedUsers] = useState([]);
    const [isListVisible, setIsListVisible] = useState(true);
    const searchBarRef = useRef(null);

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

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (searchBarRef.current && !searchBarRef.current.contains(event.target)) {
                setIsListVisible(false);
            }
        };

        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);


    const handleFilter = (event) => {
        setSearchTerm(event.target.value);
        setSelectedUsers(searchTerm);
        
        if (searchTerm === "") {
            setSelectedUsers([]);
            setIsListVisible(false);
        } 
        else {
            const newFilter = users.filter(user => 
                (user.firstName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
                 user.lastName?.toLowerCase().includes(searchTerm.toLowerCase())) || false
            );
            setSelectedUsers(newFilter);
            setIsListVisible(true);
        }
    };

    const handleSearchClick = () => {
        handleFilter();
    };


    const makeRequest = async (userID) => {
     
        const token = localStorage.getItem('jwt_token');
        try {
            const response =  await networkService.newRequest(userID,token);
            setRequestUsers(prevUsers => [...prevUsers,{ id: userID }]);
            console.log(response);
        }
        catch(error) {
            console.log("Error creating request",error);
        }
        
    }



    return (
        <div className='searchBar' ref={searchBarRef}>
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
           
            {isListVisible && searchTerm !== "" && selectedUsers.length > 0 && (
                <ul className='dataResult'>
                    {selectedUsers.slice(0, 15).map((value) => {
                        const isConnected = connectedUsers.some(user => user.id === value.id);
                        const isRequested = requestUsers.some(user => user.id === value.id);
    
                        return (
                            <li key={value.id} className="dataItem">
                                <p>
                                    <img 
                                        src={`data:image/jpeg;base64,${value.profilePicture}`} 
                                        alt='profile' 
                                        className='profile_photo' 
                                    />
                                    {value.firstName} {value.lastName}
                                    <a href={`/VisitProfile/${value.id}`}> Visit Profile </a>
    
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
                        <img src={`data:image/jpeg;base64,${connectedUser.profilePicture}`} alt='profile' className='picture' />
                       <p className='title'>{connectedUser.firstName} {connectedUser.lastName} </p> <p className='description'>{connectedUser.email}</p> <a  href={`/VisitProfile/${connectedUser.id}`} className='profile_link'>Visit Profile</a> 
                     
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