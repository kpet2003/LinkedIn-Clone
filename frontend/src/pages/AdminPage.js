import '../Admin.css';
import {useNavigate} from "react-router-dom";
import React, { useEffect, useState } from 'react';
import AdminService from '../service/adminService.js'; 






function UserList() {
    const [users, setUsers] = useState([]);
    let [selectedUsers,setSelectedUsers] = useState([]);

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

     
    const handleSelect = (userId) => {
        setSelectedUsers(prevSelectedUsers => 
            prevSelectedUsers.includes(userId) ? prevSelectedUsers.filter(id => id !== userId) : [...prevSelectedUsers, userId]
        );
    };

    // handle the select all checkbox
    const selectAll = ()=>{
        if(selectedUsers.length===users.length) {
            setSelectedUsers([]);
            return;
        }
        
        setSelectedUsers(users.map(user => user.id));
        
    }

    // export the user data in JSON form
    const exportJSON = () => {
        alert('JSON');
    }

    // export the user data in XML form
    const exportXML = () => {
        alert('XML');
    }

 
    return (
        <div>
            <table className='userlist'> 
                <thead className='header'>
                    <tr>
                        <th className='select'> <input type='checkbox' name='selectall' onChange={selectAll} checked={selectedUsers.length===users.length}/>   </th>
                        <th > Name </th>
                        <th> E-mail </th>
                        <th> Profile Page</th>
                    </tr>
                </thead>
                
                <tbody>                    
                    {users.map(user => (
                        <tr key={user.id}>
                            <td  className='select' > <input type="checkbox" name={user.id}  checked={selectedUsers.includes(user.id)} onChange={() => {handleSelect(user.id)}}/>  </td>
                            <td>{user.firstName} {user.lastName}</td>
                            <td> {user.email}</td>
                            <td> <a href={`/profile/${user.firstName}${user.lastName}`}> Visit Profile </a></td>
                        </tr>
                    ))}
                </tbody>
            </table>
            
            <input type='button' value={'Export in JSON '}className='button'onClick={exportJSON}/>
            <input type='button' value={'Export in XML '}className='button'onClick={exportXML}/>
        </div>
        
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
