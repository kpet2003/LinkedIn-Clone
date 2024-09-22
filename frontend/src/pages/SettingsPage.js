import "../styling/Settings.css";
import React, { useState } from "react";
import NavigationBar from "./NavigationBar";
import userService from "../service/userService";

function Settings() {
    //settings page is a box with a title and 2 links to pages for changing email and password
    return(
        <div className="table">
            <h1><b>Settings</b></h1><br></br>
            <nav>
                <a href="/NewEmail" className="gotolink">Change e-mail</a><br></br><br></br>
                <a href="/NewPassword" className="gotolink">Change password</a>
            </nav>
        </div>
    );
}


function SettingsPage() {


    return (
        <div>
            <NavigationBar></NavigationBar>
            <br></br>
            <Settings></Settings>
        </div>
    )
    
}

export default SettingsPage;