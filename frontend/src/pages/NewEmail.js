import '../styling/Settings.css'
import UserService from '../service/userService.js';
import React, { useState, useEffect } from 'react';
import Popup from 'reactjs-popup';



function NewEmail(){
    const [newEmail, setNewEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isPopupOpen, setIsPopupOpen] = useState(false);

    const token = UserService.decodeToken(localStorage.getItem('jwt_token'));
    const email = token.sub;

    const handleSubmit = (event) => {
        event.preventDefault();
        setIsPopupOpen(true); // Open the password popup
    };

    const handlePopupSubmit = async(event) => {
        event.preventDefault();
        
        const data = {
            token: localStorage.getItem('jwt_token'),
            newEmail: newEmail,
            password: password
        };
  
        try {
            const response =  await UserService.changeEmail(data);
            localStorage.removeItem('jwt_token');
            localStorage.setItem('jwt_token',response.data);
          
            const token_data = UserService.decodeToken(localStorage.getItem('jwt_token'));

            alert(`E-mail changed successfully to: ${token_data.sub}`);

            console.log(localStorage.getItem('jwt_token'));
            setIsPopupOpen(false);
        } 
        catch (error) {
            console.error("There was an error changing email", error);
            alert("There was an error changing your e-mail");
            setIsPopupOpen(false);
        }
    }

    const handleChange = (event) => {
        setNewEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };
    


    return(
        <div className='email-table'>
            <h1>Change Email</h1>
            <p>Your current e-mail is: {email}</p><br></br>
            <form onSubmit={handleSubmit}>
                <label>New email: </label>
                <input type='email' onChange={handleChange}></input><br></br><br></br>
                <input type='submit' value="Change" className='save-button'></input>
            </form>

            <Popup open={isPopupOpen} onClose={() => setIsPopupOpen(false)} modal className="modal-content">
                {(close) => (
                    <div className="modal-background">
                        <span className="close" onClick={close}>
                            &times;
                        </span>
                        <h2>Enter your password to confirm</h2>
                        <form onSubmit={handlePopupSubmit}>
                            <input type='password' onChange={handlePasswordChange} required/>
                            <br /><br />
                            <button type='submit' className='save-button'>Submit</button>
                        </form>
                    </div>
                )}
            </Popup>

        </div>

    );
}

export default NewEmail;
