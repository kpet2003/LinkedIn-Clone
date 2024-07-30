import '../HomePage.css';
import {useNavigate,NavLink} from "react-router-dom";
import React, { useState } from 'react';
import UserService from '../service/userService.js'; 



function NavigationBar() {
    
    return(
        <header >
            <nav className='navigation'>
                <a href='/HomePage' className='link'>HomePage </a> 
                <a href='/Network' className='link'>Network </a> 
                <a href='/Jobs' className='link'>Jobs </a> 
                <a href='/Messages' className='link'>Messages </a> 
                <a href='/Notifications' className='link'>Notifications </a> 
                <a href='/Profile' className='link'>Profile </a> 
                <a href='/Settings' className='link'>Settings </a> 
            </nav>
        </header>



    
    
  
      
        
    );
}





function HomePage() {
    return (
        <div>
         <NavigationBar/>
         {/* <Timeline/> */}
        </div>
      );
}

export default HomePage;
