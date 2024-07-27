import '../SignUp.css';
import React, { useState } from 'react'
//import userService from '../service/user.service';

function SignUpPage() {


    return (
        <div>
            <form className="signUp">
                <label>E-mail:</label><br></br>
                <input type='email' required='true'></input> <br></br>
                <label>First Name:</label><br></br>
                <input type='text'></input><br></br>
                <label>Last Name:</label><br></br>
                <input type='text'></input><br></br>
                <label>Password:</label><br></br>
                <input type='password'></input><br></br>
                <label>Repeat Password:</label><br></br>
                <input type='password'></input><br></br>
                <label>Phone Number:</label><br></br>
                <input type='tel'></input><br></br>

                <div className='profile'>
                    <label>Profile Picture:</label>
                    <input type='file' className='button'></input><br></br>
                </div>
               
                <div className='profile'>
                    <label>CV: </label>
                    <input type='file' className='button'></input><br></br>
                </div>
               
               <div className='submit'>
                    <input type='reset' value="Reset" className='button'></input>
                    <input type='button' value="Sign Up"  className='submit_button'></input>
               </div>
            </form>
        </div>
      );
}

export default SignUpPage;