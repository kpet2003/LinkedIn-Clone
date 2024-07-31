import '../Admin.css';
import {useNavigate} from "react-router-dom";
import React, { useEffect, useState } from 'react';
import AdminService from '../service/adminService.js'; 


function UserList() {

    

    const [users, setUsers] = useState([]);

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
 
    return (
  
        <table className='userlist'> 
        <thead>
            <tr>
                <th> Name </th>
                <th> E-mail </th>
                <th> Profile Page</th>
            </tr>
        </thead>
        <tbody>
            {users.map(user => (
                <tr key={user.id}>
                    <td>{user.firstName} {user.lastName}</td>
                    <td> {user.email}</td>
                    <td> <a href={`/profile/${user.firstName}${user.lastName}`}> Visit Profile </a></td>
                </tr>
            ))}
        </tbody>
    </table>    
    );

}



function AdminPage() {
    return(
        <div>
            <UserList/>
        </div>
    )
    
}

export default AdminPage;
