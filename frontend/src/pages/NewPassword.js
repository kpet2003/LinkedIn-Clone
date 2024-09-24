import '../styling/Settings.css';
import UserService from '../service/userService.js';
import React, { useState } from 'react';
import NavigationBar from './NavigationBar.js';

function ChangePassword(){
    const [pass, setPass] = useState(''); //to keep new password
    const [passrep, setPassRep] = useState(''); //to keep new password repeated

    //handle password change
    const handleSubmit = async(event) => {
        event.preventDefault(); //prevent default get request
        
        if(pass !== passrep){ //if passwords don't match return
            alert("Passwords don't match");
            return;
        }

        //make data to be sent to database
        const data = {
            token: localStorage.getItem('jwt_token'),
            newPassword: pass
        };
  
        try {
            await UserService.changePassword(data); //change password
            alert("Password changed successfully"); //message to user
        } 
        catch (error) { //if error occurs
            console.error("There was an error changing password", error); //write to console
            alert("There was an error changing your password"); //message to user
        }
    }

    //handle user inputting password
    const handleChange1 = (event) => {
        setPass(event.target.value); //save value
    };

    //handle user inputting password repeated
    const handleChange2 = (event) => {
        setPassRep(event.target.value); //save value
    };

    return(
        <div>
           <div className='password-table'> {/* box with inputs for the new password and a submit button */}
            <h1>Change Password</h1><br></br>
            <form onSubmit={handleSubmit}>
                <label>New Password: </label>
                <input type='password' onChange={handleChange1} style={{ outline: 'none' }}></input><br></br><br></br>
                <label>Repeat New Password: </label>
                <input type='password' onChange={handleChange2} style={{ outline: 'none' }} ></input><br></br><br></br>
                <input type='submit' value="Change" className='save-button'></input>
            </form>
        </div> 
        </div>
    );
}


function NewPassword() {
    return(
        <div>
            <NavigationBar></NavigationBar>
            <br></br>
            <ChangePassword></ChangePassword>
        </div>
    );
}
export default NewPassword;