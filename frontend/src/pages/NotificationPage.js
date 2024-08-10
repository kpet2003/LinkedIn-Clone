import React, { useState, useEffect } from 'react';
import NavigationBar from './HomePage.js'
import userService from "../service/userService.js";
import Popup from 'reactjs-popup';


function Requests() {
    
}

function Notifications(){
    return(
        <div>
            <NavigationBar></NavigationBar>
        </div>
    );
}

export default Notifications;