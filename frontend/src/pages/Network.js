import NavigationBar from './NavigationBar.js';
import React, { useState, useEffect, useRef, useCallback } from 'react';
import glass from "../icons/glass.png";
import '../styling/Network.css';
import networkService from '../service/networkService.js'; 
import UserService from '../service/userService.js';
import placeholder from '../icons/avatar.png';

function SearchBar({users,requestUsers,connectedUsers,setRequestUsers}) {

    
    const [selectedUsers,setSelectedUsers] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [isListVisible, setIsListVisible] = useState(true);
    const searchBarRef = useRef(null);
  

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


    const makeRequest =  useCallback(async(userID) => {
     
        const token = localStorage.getItem('jwt_token');
        try {
            const response =  await networkService.newRequest(userID,token);
            setRequestUsers(prevUsers => [...prevUsers,{ id: userID }]);
            console.log(response);
        }
        catch(error) {
            console.log("Error creating request",error);
        }
        
    },[setRequestUsers]);



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
                                <img src={value.profilePicture?`data:image/jpeg;base64,${value.profilePicture}`:placeholder } alt = 'profile'className='picture'/>
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
    const [users, setUsers] = useState([]);
    const [connectedUsers,setConnectedUsers] = useState([]);
    const [requestUsers,setRequestUsers] = useState([]);


        // renders the list of existing users, except for the admin 
        useEffect(() => {
            const token = localStorage.getItem('jwt_token');
            const email = UserService.decodeToken(token).sub;
    
            const fetchData = async () => {
                try {
                    const [usersResponse, requestsResponse, connectionsResponse] = await Promise.all([
                        networkService.getUsers(),
                        networkService.fetchRequests(token),
                        networkService.fetchConnections(token)
                    ]);
                    const finalUsers = usersResponse.filter(user => user.email !== 'admin@gmail.com' && 
                        user.email !== email &&
                        !connectedUsers.includes(user.id) &&
                        !requestUsers.includes(user.id))
                    setUsers(finalUsers);
                    setConnectedUsers(connectionsResponse);
                    setRequestUsers(requestsResponse);
                } 
                catch (error) {
                    console.error("There was an error getting the user list", error);
                }
            }
            fetchData();
        },[]);



    return(
        <div >
            <SearchBar users={users} requestUsers={requestUsers} connectedUsers={connectedUsers} setRequestUsers={setRequestUsers}/>
            <div className='net'>
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



function Network(){
    return(
        <div>
            <NavigationBar></NavigationBar>
            <MyNetwork></MyNetwork>
        </div>
    );
}

export default Network;