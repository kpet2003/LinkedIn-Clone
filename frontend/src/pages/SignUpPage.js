import '../SignUp.css';
import React, { useState } from 'react'
import UserService from '../service/userService.js'; 

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

    const handleSubmit = async(event) => {
        event.preventDefault();
        
        if(user.password!==user.repeat_password) {
            alert("Passwords don't match");
            return;
        }

        const UserData = {
            first_name: user.first_name,
            last_name: user.last_name,
            email: user.email,
            password: user.password,
            phone: user.phone,
            profile_picture: user.profile_picture,
            cv: user.cv
        }

        try {
            const response =  await UserService.saveUser(user);
            alert("User registered successfully");
            console.log(response.data);  // Handle the response as needed
        } catch (error) {
            console.error("There was an error registering the user:", error);
            alert("There was an error registering the user.");
        }


    }

    const handleChange = (event) => {
        event.preventDefault();
        setUser({
          ...user,
          [event.target.id]: event.target.value,
        });
    };

    const handleFiles = (event) => {
        event.preventDefault();

        const { id, files } = event.target;
        setUser(prevState => ({
            ...prevState,
            [id]: files[0]
        }));
    }

    const Reset = () => {
        setUser(initialState);  
        document.getElementById('profile_picture').value = '';
        document.getElementById('cv').value = '';
    };


    return (
        <div>
            <h1> Sign up</h1>
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
                    <input type='file' className='button' onChange={handleFiles} id="profile_picture"></input><br></br>
                </div>
               
                <div className='profile'>
                    <label>CV: </label>
                    <input type='file' className='button' onChange={handleFiles}  id="cv" ></input><br></br>
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