import React from 'react';
import {Routes, Route} from 'react-router-dom';
import { useState,useEffect } from 'react';

import ProtectedRoute from './ProtectedRoute';
import WelcomePage from "../pages/WelcomePage"
import SignUpPage from "../pages/SignUpPage"
import HomePage from "../pages/HomePage"
import AdminPage from '../pages/AdminPage';
import ProfilePage from '../pages/ProfilePage';
import SettingsPage from '../pages/SettingsPage';
import NewEmail from '../pages/NewEmail';
import NewPassword from '../pages/NewPassword';
import Notifications from '../pages/NotificationPage';

const Main=()=>{
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        // Check if the user is authenticated
        const token = localStorage.getItem('jwt_token');
        if (token) {
            setIsAuthenticated(true);
        } 
        else {
            setIsAuthenticated(false);
        }
    }, []);


    return(
        <Routes>
            <Route exact path='/' Component={WelcomePage}></Route>
            <Route exact path='/SignUp' Component={SignUpPage}></Route>
            <Route element={<ProtectedRoute isAuthenticated={isAuthenticated} />}>
                <Route exact path='/AdminPage' Component={AdminPage}></Route>
                <Route exact path='/HomePage' Component={HomePage}></Route>
            </Route>
            <Route exact path='/Notifications' Component={Notifications}></Route>
            <Route exact path='/Profile' Component={ProfilePage}></Route>
            <Route exact path='/Settings' Component={SettingsPage}></Route>
            <Route exact path='/NewEmail' Component={NewEmail}></Route>
            <Route exact path='/NewPassword' Component={NewPassword}></Route>
            
        </Routes>
    );
}

export default Main;