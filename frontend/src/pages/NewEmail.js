import '../Settings.css'
import UserService from '../service/userService.js';
import React, { useState, useEffect } from 'react';



function NewEmail(){
    const userID = localStorage.getItem('userID');

    const initialState = {
        email: ''
    };

    const [user, setUser] = useState(initialState);

    useEffect(() => {
    const fetchUser = async () => {
        try{
            const userData = await UserService.getUser(userID,"/NewEmail");
            setUser(userData);  
        }
        catch(err){
            throw Error('DID NOT GET USER DATA');
        }
        
    };
    fetchUser();
    },[userID]);
    
    return(
        <div className='email-table'>
            <h1>Change Email</h1>
            <p>Your current email is: {user.email}</p><br></br>
            <form>
                <label>New email: </label>
                <input type='email'></input><br></br><br></br>
                <input type='submit' value="Change" ></input>
            </form>
        </div>
    );
}

export default NewEmail;