import '../Settings.css'
import UserService from '../service/userService.js';
import React, { useState, useEffect } from 'react';



function NewEmail(){
    const [email, setEmail] = useState('');
    const [newEmail, setNewEmail] = useState('');

    useEffect(() => {
    const fetchUser = async () => {
        try{
            const emailData = await UserService.getUserEmail(localStorage.getItem('userID'));
            setEmail(emailData);  
        }
        catch(err){
            console.log(err);
            alert('DID NOT GET USER DATA');
        }
        
    };
        fetchUser();
    },[]);
    

    const handleSubmit = async(event) => {
        event.preventDefault();
        
        const formData = new FormData();
        formData.append('email', newEmail);
        formData.append('id', localStorage.getItem('userID'));
  
        try {
            const response =  await UserService.changeEmail(formData);
            alert("E-mail changed successfully to: ", response);
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
