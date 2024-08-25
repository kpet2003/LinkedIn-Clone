import '../styling/SignUp.css';
import React, { useState } from 'react'
import UserService from '../service/userService.js';
import {useNavigate} from "react-router-dom";

function SignUpPage(){

    const initialState = {
        first_name: '',
        last_name: '',
        email: '',
        password: '',
        repeat_password: '',
        phone: '',
        profile_picture: null,
        cv: null
    };

    const [user, setUser] = useState(initialState);

    const navigate = useNavigate();
    const handleSubmit = async(event) => {
        event.preventDefault();
        
        if(user.password!==user.repeat_password) {
            alert("Passwords don't match");
            return;
        }

        const formData = new FormData();
        formData.append('email', user.email);
        formData.append('firstName', user.first_name);
        formData.append('lastName', user.last_name);
        formData.append('password', user.password);
        formData.append('phoneNumber', user.phone);
        formData.append('profilePicture', user.profile_picture);
        formData.append('resume', user.cv);


        try {
            const response =  await UserService.saveUser(formData);
            alert("User registered successfully");
            console.log(response.data);
            navigate(-1);  
        } catch (error) {
            console.error("There was an error registering the user:", error.response.data);
            const errorMessage = `There was an error registering the user: ${error.response.data}`;
            alert(errorMessage);
            setUser(initialState);
        }
    }
    const handleChange = (event) => {
        setUser({
            ...user,
            [event.target.id]: event.target.value,
        });
    };
    
    const handleFiles = (event) => {
        const { id, files } = event.target;
        setUser(prevState => ({
            ...prevState,
            [id]: files[0]
        }));
    };
    

    const Reset = () => {
        setUser(initialState);  
        document.getElementById('profile_picture').value = '';
        document.getElementById('cv').value = '';
    };


    return (
        <div>
            <form className="signUp" onSubmit={handleSubmit}>
                <label>E-mail:</label><br></br>
                <input type='email' required value = {user.email} onChange={handleChange}  id="email"></input> <br></br>
                <label>First Name: </label><br></br>
                <input type='text' required   value = {user.first_name} onChange={handleChange}  id="first_name"></input><br></br>
                <label>Last Name:</label><br></br>
                <input type='text' required onChange={handleChange}  value = {user.last_name} id="last_name"></input><br></br>
                <label>Password:</label><br></br>
                <input type='password' required onChange={handleChange}  value = {user.password} id="password"></input><br></br>
                <label>Repeat Password: </label><br></br>
                <input type='password' required onChange={handleChange}  value = {user.repeat_password} id="repeat_password" ></input><br></br>
                <label>Phone Number: </label><br></br>
                <input type='tel' onChange={handleChange} value = {user.phone}  id="phone"></input><br></br>

                <div className='profile'>
                    <label>Profile Picture:</label>
                    <input type='file' onChange={handleFiles} id="profile_picture"></input><br></br>
                </div>
               
                <div className='profile'>
                    <label>CV: </label>
                    <input type='file' onChange={handleFiles}  id="cv" ></input><br></br>
                </div>
               
               <div className='submit'>
                    <input type='reset' value="Reset" className='button' onClick={Reset} ></input>
                    <input type='submit' value="Sign Up"  className='submit_button'></input>
               </div>
            </form>
        </div>
      );
}

export default SignUpPage;