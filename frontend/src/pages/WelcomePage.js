import '../App.css';
import {useNavigate} from "react-router-dom";
import React, { useState } from 'react';
import UserService from '../service/userService.js'; 

function ClickButton() {
    const navigate = useNavigate();
    
    const SignUpPage=()=>{
      navigate('/SignUp');
    };


    const initialState = {
        email: '',
        password: '',
    };

    const [user, setUser] = useState(initialState);

    const handleSubmit = async(event) => {
        event.preventDefault();
        
        const formData = new FormData();
        formData.append('email', user.email);
        formData.append('password', user.password);

        try {
            const response =  await UserService.loginUser(formData);
            if(response.data){
              navigate('/AdminPage');
            }
            else{
              navigate('/HomePage');
            }
        } 
        catch (error) {
            console.error("There was an error registering the user:", error);
            alert("There was an error registering the user.");
        }
    }
    
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
  }
  
  function WelcomePage(){
    return (
      <div>
       <ClickButton />
      </div>
    );
  }

  export default WelcomePage;

