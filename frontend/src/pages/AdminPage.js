import '../styling/Admin.css';
import React, { useEffect, useState } from 'react';
import AdminService from '../service/adminService.js'; 
import placeholder from '../icons/avatar.png';
import adminService from '../service/adminService.js';



function UserList() {
    const [users, setUsers] = useState([]);
    let [selectedUsers,setSelectedUsers] = useState([]);


    // renders the list of existing users, except for the admin 
    useEffect(() => {

        const cancelUser = new AbortController();

        const fetchUsers = async() => {
            try {
                const response = await  AdminService.getUsers(cancelUser);
                const finalUsers = response.filter(user => user.email !== 'admin@gmail.com');
                setUsers(finalUsers);

            } 
            catch (error) {
                console.error("There was an error getting the user list", error);
            }
        };
        fetchUsers();

        return () => {
            cancelUser.abort();  
          };

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

        const excludeAttributes = ['profilePicture'];
            const finalUsers = users.filter(user => selectedUsers.includes(user.id)).map(user => {
            const { [excludeAttributes[0]]: _, ...rest } = user;
            return rest;
        });
            
        
            const jsonData = new Blob([JSON.stringify(finalUsers, null, 2)], { type: 'application/json' });
            const jsonURL = URL.createObjectURL(jsonData);
            const link = document.createElement('a');
            link.href = jsonURL;
            link.download = `users.json`;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
    }


    // export the user data in XML form
    const exportXML = async() => {
        
        try {
            const response = await adminService.getxmlUsers(selectedUsers);
            console.log(response);

            
            const blob = new Blob([response], { type: "application/xml"});
            const url = URL.createObjectURL(blob);
            const link = document.createElement("a");
            link.download = "users.xml";
            link.href = url;
            link.click();

            
        }
        catch(error) {
            console.log('error fetching xml data: ',error);
        }


        
      
    }

 
    return (
        <div className='admin'>
           
            <div className='buttons'>
                <input type='button' value={'Export in JSON '}className='button'onClick={exportJSON}/>
                <input type='button' value={'Export in XML '}className='button'onClick={exportXML}/>
                <input type='button' value={'Select All '}className='button'onClick={selectAll}/>
            </div>

            <div className='userlist'> 
                {users.map(user => (
            
                    <span key={user.id}  className={`user ${selectedUsers.includes(user.id) ? 'selected' : ''}`} onClick={()=>handleSelect(user.id)}>
                         <img src={user.profilePicture?`data:image/jpeg;base64,${user.profilePicture}`:placeholder } alt = 'profile'className='picture'/>
                       <p className='title'>{user.firstName} {user.lastName} </p>
                        <p className='description'>{user.email}</p> 
                       <a  href={`/VisitProfile/${user.id}`} className='profile_link' >Visit Profile</a> 
                    </span>   
                ))}
            </div>

            
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
