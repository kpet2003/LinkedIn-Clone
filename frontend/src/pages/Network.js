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

    // renders the list of existing users, except for the admin 
    useEffect(() => {
        const fetchUsers = async() => {
            try {
                const response = await  AdminService.getUsers();
                const finalUsers = response.filter(user => user.admin !== true);
                setUsers(finalUsers);
            } 
            catch (error) {
                console.error("There was an error getting the user list", error);
            }
        };
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
                <div className="dataResult">
                    {selectedUsers.slice(0, 15).map((value) => (
                        <div key={value.id} className="dataItem">
                            <p>
                                <img 
                                    src={`data:image/jpeg;base64,${value.profilePicture}`} 
                                    alt='profile' 
                                    className='profile_photo' 
                                />
                                {value.firstName} {value.lastName}
                                <a href='/Profile'> Visit Profile </a>


                                <input 
                                type='button' 
                                value='Make connection' 
                                className='button'
                                onClick={() => makeRequest(value.id)} 
                            />
                            </p>
                           
                        </div>
                    ))}
                </div>
            )}
  
        </div>
    );
}



function Network(){
    return(
        <div>
            <NavigationBar></NavigationBar>
            <SearchBar></SearchBar>

        </div>
    );
}

export default Network;