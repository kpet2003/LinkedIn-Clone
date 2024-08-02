import '../Settings.css'
import UserService from '../service/userService.js';
import React, { useState, useEffect } from 'react';



function NewEmail(){
    let userID = localStorage.getItem('userID');

    const [email, setEmail] = useState('');

    useEffect(() => {
    const fetchUser = async () => {
        try{
            const emailData = await UserService.getUserEmail(userID);
            setEmail(emailData);  
        }
        catch(err){
            console.log(err);
            throw Error('DID NOT GET USER DATA');
        }
        
    };
    fetchUser();
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