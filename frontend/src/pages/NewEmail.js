import '../Settings.css'
import UserService from '../service/userService.js';
import React, { useState, useEffect } from 'react';



function NewEmail(){
    const userID = localStorage.getItem('userID');
    const [email, setEmail] = useState('');

    useEffect(() => {
    const fetchUser = async () => {
        try{
            const emailData = await UserService.getUserEmail(userID);
            console.log('email data ', emailData);
            setEmail(emailData);  
        }
        catch(err){
            console.log(err);
            alert('DID NOT GET USER DATA');
        }
        
    };
    if (userID) {
        fetchUser();
    }
    },[userID]);
    

    return(
        <div className='email-table'>
            <h1>Change Email</h1>
            <p>Your current email is: {email}</p><br></br>
            <form>
                <label>New email: </label>
                <input type='email'></input><br></br><br></br>
                <input type='submit' value="Change" ></input>
            </form>
        </div>
    );
}

export default NewEmail;
