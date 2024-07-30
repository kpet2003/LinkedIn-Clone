import '../App.css';
import {useNavigate,NavLink} from "react-router-dom";
import React, { useState } from 'react';
import UserService from '../service/userService.js'; 



function NavigationBar() {
    
    return(
        <nav>
            Navigate
        </nav>
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
