import React from "react";
import NavigationBar from './HomePage.js'
import userService from "../service/userService.js";
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

function VisitProfile(){
    const initialState = {
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        profilePicture: '',
        resume: null,
        workExperience: '',
        education: '',
        skills: '',
        publicWork: '',
        publicSkills: '',
        publicEducation: ''
    };

    const [user, setUser] = useState(initialState);
    const { userId } = useParams();

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const data = await userService.getProfile(userId);
                setUser((user) => ({
                    ...user,
                    ...data
                }));
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };
        fetchUserData();
    }, [userId]);

    const base64Image = `data:image/jpeg;base64,${user.profilePicture}`;

    return(
        <div>
            <NavigationBar></NavigationBar>
            <br></br>
            <div>
                <div className="banner">
                    <div className="profile-picture-container">
                        <img
                        src={base64Image}
                        alt="Profile"
                        className="profile-picture"
                        />
                    </div>
                    <div>
                        <h1 className="username">{user.firstName} {user.lastName} </h1>
                    </div>
                </div>
                <br></br>
                <br></br>
                <div className="info">
                    <h2>About me</h2><br></br>
                    <div className="header-container">
                    <h3 className="title">Education</h3>
                    </div>
                    <p>{user.education}</p>
                    <div className="header-container">
                    <h3 className="title">Work Experience</h3>
                    </div>
                    <p>{user.workExperience}</p>
                    <div className="header-container">
                    <h3 className="title">Skills</h3>
                    </div>
                    <p>{user.skills}</p>
                </div>
            </div>
        </div>
    );
}

export default VisitProfile