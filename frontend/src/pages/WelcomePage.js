import '../App.css';
import {useNavigate} from "react-router-dom";
import React, { useState } from 'react'

function ClickButton() {
    const navigate = useNavigate();
    const SignUpPage=()=>{
      navigate('/SignUp');
    };
    return (
      <div className='login'>
         <h1 >Welcome</h1>
        <form>
          <label>E-mail:</label><br></br>
          <input type='email'></input><br></br>
          <label>Password:</label><br></br>
          <input type='password'></input><br></br>
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

