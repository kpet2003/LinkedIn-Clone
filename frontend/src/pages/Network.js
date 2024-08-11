import NavigationBar from './HomePage.js'
import React, { useEffect, useState } from 'react';
import AdminService from '../service/adminService.js'; 
import glass from "../glass.png";
import '../Network.css';

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

    return (
        <div className='searchBar'>
            <img src = {glass} alt='search' className='photo' onClick={handleFilter}/> 
            <input type='text'  value={searchTerm}  onChange={handleFilter} className='search' placeholder='Search Users'></input>
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