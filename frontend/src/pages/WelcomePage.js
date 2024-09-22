import '../styling/App.css';
import {useNavigate} from "react-router-dom";
import React, { useState } from 'react';
import UserService from '../service/userService.js'; 

  
const WelcomePage = ({ onLoginSuccess }) => {
    const navigate = useNavigate();
    
    // button for navigating to signup page
    const SignUpPage=()=>{
      navigate('/SignUp');
    };

    // initial login form
    const initialState = {
        email: '',
        password: '',
    };

    const [user, setUser] = useState(initialState);

    
    // handles the login process
    const handleSubmit = async(event) => {
      event.preventDefault();
      
      const data = {
        email: user.email,
        password: user.password
      };

      try {

          // sends login request
          const response =  await UserService.loginUser(data);

          // stores jwt token that was received from the backend
          localStorage.setItem('jwt_token',response.data);
          
          const token_data = UserService.decodeToken(localStorage.getItem('jwt_token'));

          // redirect admin to admin page and user to their homepage      
          if(token_data.sub === 'admin@gmail.com'){
            onLoginSuccess(true);
          }
          else{
            onLoginSuccess(false);
          }
      } 
      catch (error) {
        // if login fails alert the user
        console.error("Login error:", error);
        alert("Wrong email or password");
      }
  }
    // handler of user input
    const handleChange = (event) => {
        setUser({
            ...user,
            [event.target.id]: event.target.value,
        });
    };

    return (
      <div className='login'>
         <h1 >Welcome</h1>
        <form onSubmit={handleSubmit}>
          <label>E-mail:</label><br></br>
          <input type='email' required value = {user.email} onChange={handleChange}  id="email"></input><br></br>
          <label>Password:</label><br></br>
          <input type='password' required value = {user.password} onChange={handleChange}  id="password"></input><br></br>
          <input className='LoginButton' type='submit' value={'Login'}></input><br></br>
          <div className='signup'>
            Create an account:
            <input className='WelcomeButton' type='button' value={'Sign Up'} onClick={SignUpPage}></input>
          </div>
        </form>
      </div>
    );
  };

  export default WelcomePage;

