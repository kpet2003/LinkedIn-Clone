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
import Network from '../pages/Network';
import VisitProfile from '../pages/VisitProfilePage';

const Main=()=>{
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    const checkAuthentication = () => {
        const token = localStorage.getItem('jwt_token');
        if (token !== null) {
        setIsAuthenticated(true);
        } else {
        console.log('User not authenticated');
        setIsAuthenticated(false);
        }
        setIsLoading(false); // Loading complete
    };

    useEffect(() => {
        checkAuthentication();
    }, []);

    const handleLoginSuccess = () => {
        checkAuthentication();
    };

    if (isLoading) {
        return <div>Loading...</div>; // Show a loading state until auth status is determined
    }

    return(
        <Routes>
            <Route exact path='/' element={<WelcomePage onLoginSuccess={handleLoginSuccess} />} ></Route>
            <Route exact path='/SignUp' Component={SignUpPage}></Route>
            <Route element={<ProtectedRoute isAuthenticated={isAuthenticated} />}>
                <Route exact path='/AdminPage' Component={AdminPage}></Route>
                <Route exact path='/HomePage' Component={HomePage}></Route>
                <Route exact path='/Network' Component={Network}></Route>
                <Route exact path='/Notifications' Component={Notifications}></Route>
                
                <Route exact path='/Settings' Component={SettingsPage}></Route>
                <Route exact path='/NewEmail' Component={NewEmail}></Route>
                <Route exact path='/NewPassword' Component={NewPassword}></Route>
                
            </Route>
            <Route exact path='/Profile' Component={ProfilePage}></Route>
            <Route exact path='/VisitProfile/:id' Component={VisitProfile}></Route> 
        </Routes>
    );
}

export default Main;