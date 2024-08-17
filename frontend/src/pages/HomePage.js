import '../HomePage.css';
import {useNavigate,NavLink} from "react-router-dom";
import React, { useState } from 'react';
import UserService from '../service/userService.js'; 
import home from '../icons/home.png';
import network from '../icons/network.png';
import jobs from '../icons/jobs.png';
import message from '../icons/text-bubble.png';
import bell from '../icons/bell.png';
import settings from '../icons/settings.png'
import profile from '../icons/profile.png';



function NavigationBar() {
    
    return(
        <div >
            <nav className='navigation'>
                <div className='link-container'>
                <img src={home} alt='' className='home-icon'></img>
                <a href='/HomePage' className='link'>Home </a> 
                </div>
                <div className='link-container'>
                <img src={network} alt='' className='net-icon'></img>
                <a href='/Network' className='link'>Network </a> 
                </div>
                <div className='link-container'>
                <img src={jobs} alt='' className='icons'></img>
                <a href='/Jobs' className='link'>Jobs </a> 
                </div>
                <div className='link-container'>
                <img src={message} alt='' className='text-icon'></img>
                <a href='/Messages' className='link'>Messages </a> 
                </div>
                <div className='link-container'>
                <img src={bell} alt='' className='icons'></img>
                <a href='/Notifications' className='link'>Notifications </a> 
                </div>
                <div className='link-container'>
                <img src={profile} alt='' className='profile-icon'></img>
                <a href='/Profile' className='link'>Profile </a> 
                </div>
                <div className='link-container'>
                <img src={settings} alt='' className='icons'></img>
                <a href='/Settings' className='link'>Settings </a> 
                </div>
            </nav>
        </div>
        
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
