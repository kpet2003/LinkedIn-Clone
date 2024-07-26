import '../SignUp.css';
import React, { useState } from 'react'
import userService from '../service/user.service';

function SignUpPage() {

    const [user, setUser] = useState({
        first_name: "",
        last_name: "",
        email: "",
        phone_number: "",
        password: "",
    });
    const [msg, setMsg] = useState("")

    const handleChange = (e) => {
        const value = e.target.value;
        setUser({ ...user, [e.target.name]: value })
    }

    const RegisterUser = (e) => {
        e.preventDefault();
        console.log(user);
        userService.saveUser(user)
            .then((res) => {
                console.log("User Added Successfully");
                setMsg("Used Added Sucessfully");
                setUser({
                    userName: "",
                    email: "",
                    mobile: "",
                    address: "",
                })
            }).catch((error) => {
                console.log(error);
            });
    }


    return (
        <div>
            <form className="signUp">
                <label>E-mail:</label><br></br>
                <input type='email'></input> <br></br>
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
                    <label>Reset:</label>
                    <input type='reset' value="Reset" className='button'></input>
                    <input type='button' value="Sign Up"  className='submit_button'></input>
               </div>
            </form>
        </div>
      );
}

export default SignUpPage;