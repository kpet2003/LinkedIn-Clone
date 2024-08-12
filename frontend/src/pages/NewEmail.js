import '../Settings.css'
import UserService from '../service/userService.js';
import React, { useState, useEffect } from 'react';



function NewEmail(){
    const [newEmail, setNewEmail] = useState('');

    const token = UserService.decodeToken(localStorage.getItem('jwt_token'));
    const email = token.sub;

    const handleSubmit = async(event) => {
        event.preventDefault();
        
        const data = {
            token: localStorage.getItem('jwt_token'),
            newEmail: newEmail
        };
  
        try {
            const response =  await UserService.changeEmail(data);
            localStorage.removeItem('jwt_token');
            localStorage.setItem('jwt_token',response.data);
          
            const token_data = UserService.decodeToken(localStorage.getItem('jwt_token'));

            alert("E-mail changed successfully to: ", token_data.sub);
        } 
        catch (error) {
            console.error("There was an error changing email", error);
            alert("There was an error changing your e-mail");
        }
    }

    const handleChange = (event) => {
        setNewEmail(event.target.value);
    };


    return(
        <div className='email-table'>
            <h1>Change Email</h1>
            <p>Your current e-mail is: {email}</p><br></br>
            <form onSubmit={handleSubmit}>
                <label>New email: </label>
                <input type='email' onChange={handleChange}></input><br></br><br></br>
                <input type='submit' value="Change" ></input>
            </form>
        </div>
    );
}

export default NewEmail;
