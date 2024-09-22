import '../styling/Settings.css'
import UserService from '../service/userService.js';
import React, { useState } from 'react';
import Popup from 'reactjs-popup';
import NavigationBar from './NavigationBar.js';


function NewMail(){
    const [newEmail, setNewEmail] = useState(''); //to keep the new email
    const [password, setPassword] = useState(''); //to keep the user's password
    const [isPopupOpen, setIsPopupOpen] = useState(false); //to check if verification popup is open

    //get user's email from token kept in local storage
    const token = UserService.decodeToken(localStorage.getItem('jwt_token'));
    const email = token.sub;

    //function to ask for the user's password to verify
    const handleSubmit = (event) => {
        event.preventDefault(); //prevent default get request
        setIsPopupOpen(true); //show the popup
    };

    //function to handle the user giving their password
    const handlePopupSubmit = async(event) => {
        event.preventDefault();//prevent default get request
        
        //make data to pass to database
        const data = {
            token: localStorage.getItem('jwt_token'),
            newEmail: newEmail,
            password: password
        };
  
        try {
            const response =  await UserService.changeEmail(data); //change the email in the database and get new token
            localStorage.removeItem('jwt_token'); //delete old token
            localStorage.setItem('jwt_token',response.data); //save new token
          
            const token_data = UserService.decodeToken(localStorage.getItem('jwt_token')); //get new email from new token

            alert(`E-mail changed successfully to: ${token_data.sub}`); //message to user

            setIsPopupOpen(false); //close popup
        } 
        catch (error) { //if error occurs
            console.error("There was an error changing email", error); //write to console
            alert("There was an error changing your e-mail"); //message to user
            setIsPopupOpen(false); //close popup
        }
    }

    //function to handle user inputting email
    const handleChange = (event) => {
        setNewEmail(event.target.value); //save new email to constant
    };

    //function to handle user inputting password
    const handlePasswordChange = (event) => {
        setPassword(event.target.value); //save password to constant
    };
    


    return(
        <div className='email-table'> {/* box that shows the area to change the email and a submit button */}
            <h1>Change Email</h1>
            <p>Your current e-mail is: {email}</p><br></br>
            <form onSubmit={handleSubmit}>
                <label>New email: </label>
                <input type='email' onChange={handleChange} style={{ outline: 'none' }}></input><br></br><br></br>
                <input type='submit' value="Change" className='save-button'></input>
            </form>

            {/* popup to verify password */}
            <Popup open={isPopupOpen} onClose={() => setIsPopupOpen(false)} modal className="modal-content">
                {(close) => (
                    <div className="modal-background">
                        <span className="close" onClick={close}>
                            &times;
                        </span>
                        <h2>Enter your password to confirm</h2>
                        <form onSubmit={handlePopupSubmit}>
                            <input type='password' onChange={handlePasswordChange} required style={{ outline: 'none' }} />
                            <br /><br />
                            <button type='submit' className='save-button'>Submit</button>
                        </form>
                    </div>
                )}
            </Popup>

        </div>

    );
}

function NewEmail() {
    return (
        <div>
            <NavigationBar></NavigationBar>
            <br></br>
            <NewMail></NewMail>
        </div>
    );
}

export default NewEmail;
