import '../Admin.css';
import {useNavigate} from "react-router-dom";
import React, { useEffect, useState } from 'react';
import AdminService from '../service/adminService.js'; 


function UserList() {

    

    const [users, setUsers] = useState([]);

    useEffect(() => {
        const fetchUsers = async() => {
            try {
                const response =  AdminService.getUsers();
                setUsers(response);
                alert(JSON.stringify(users));
                console.log(JSON.stringify(users));
            } 
            
            catch (error) {
                console.error("There was an error getting the user list:", error);
            }
        };
        fetchUsers();
    }, []);
 


    


    return (
  
        <table className='userlist'> 
            <tr>
                <th> Name </th>
                <th> E-mail </th>
                <th> Profile Page</th>
            </tr>
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
