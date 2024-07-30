import React from 'react';
import {Routes, Route} from 'react-router-dom';
import WelcomePage from "../pages/WelcomePage"
import SignUpPage from "../pages/SignUpPage"
import HomePage from "../pages/HomePage"
import AdminPage from '../pages/AdminPage';

const Main=()=>{
    return(
        <Routes>
            <Route exact path='/' Component={WelcomePage}></Route>
            <Route exact path='/SignUp' Component={SignUpPage}></Route>
            <Route exact path='/AdminPage' Component={AdminPage}></Route>
            <Route exact path='/HomePage' Component={HomePage}></Route>
        </Routes>
    );
}

export default Main;