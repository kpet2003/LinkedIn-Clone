import React from 'react';
import {Routes, Route} from 'react-router-dom';
import WelcomePage from "../pages/WelcomePage"
import SignUpPage from "../pages/SignUpPage"

const Main=()=>{
    return(
        <Routes>
            <Route exact path='/' Component={WelcomePage}></Route>
            <Route exact path='/SignUp' Component={SignUpPage}></Route>
        </Routes>
    );
}

export default Main;