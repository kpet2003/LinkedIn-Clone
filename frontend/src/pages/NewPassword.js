import '../styling/Settings.css';
import UserService from '../service/userService.js';
import React, { useState } from 'react';
import NavigationBar from './NavigationBar.js';

function ChangePassword(){
    const [pass, setPass] = useState('');
    const [passrep, setPassRep] = useState('');

    const handleSubmit = async(event) => {
        event.preventDefault();
        
        if(pass !== passrep){
            alert("Passwords don't match");
            return;
        }

        const data = {
            token: localStorage.getItem('jwt_token'),
            newPassword: pass
        };
  
        try {
            await UserService.changePassword(data);
            alert("Password changed successfully");
        } 
        catch (error) {
            console.error("There was an error changing password", error);
            alert("There was an error changing your password");
        }
    }

    const handleChange1 = (event) => {
        setPass(event.target.value);
    };

    const handleChange2 = (event) => {
        setPassRep(event.target.value);
    };

    return(
        <div>
           <div className='password-table'>
            <h1>Change Password</h1><br></br>
            <form onSubmit={handleSubmit}>
                <label>New Password: </label>
                <input type='password' onChange={handleChange1} style={{ outline: 'none' }}></input><br></br><br></br>
                <label>Repeat New Password: </label>
                <input type='password' onChange={handleChange2} style={{ outline: 'none' }} ></input><br></br><br></br>
                <input type='submit' value="Change"></input>
            </form>
        </div> 
        </div>
    );
}


function NewPassword() {
    return(
        <div>
            <NavigationBar></NavigationBar>
            <ChangePassword></ChangePassword>
        </div>
    );
}
export default NewPassword;