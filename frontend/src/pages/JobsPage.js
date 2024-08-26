
import '../styling/HomePage.css';
import React, { useEffect, useState,Suspense } from 'react';
import userService from "../service/userService.js";
import image_upload from '../icons/image_upload.png';
import video_upload from '../icons/video_upload.png';
import ArticleService from '../service/articleService.js';
import NavigationBar from './NavigationBar.js';
import {useNavigate} from "react-router-dom";



function Jobs() {
    return (
        <div>
            My job feed:
        </div>
    )
}

function JobPage() {
    return (
        <div>
            <NavigationBar></NavigationBar>
            <Jobs></Jobs>
        </div>
     
    );
}

export default JobPage;