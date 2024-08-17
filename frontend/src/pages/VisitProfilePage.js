import React from "react";
import NavigationBar from './HomePage.js'
import userService from "../service/userService.js";
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import '../Visit.css'
import placeholder from '../icons/avatar.png'

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
        publicEducation: '',
        workTitle: '',
        workplace: '',
        website: ''
    };

    const [user, setUser] = useState(initialState);
    const { id } = useParams();
    console.log('userId is',id);

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const data = await userService.getProfile(id);
                setUser((user) => ({
                    ...user,
                    ...data
                }));
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };
        fetchUserData();
    }, [id]);

    const base64Image = user.profilePicture? `data:image/jpeg;base64,${user.profilePicture}`: `${placeholder}`;

    return(
        <div>
            <NavigationBar></NavigationBar>
            <br></br>
            <div>
                <div className="banner">
                    <div className="profile-picture-container add-margin">
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
                <div className="info split left">
                    <div className="resume-card">
                        <h2>
                            <i>Work</i>
                        </h2>
                        <h3>Working as</h3>
                        <p>{user.workTitle}</p>
                        <h3>Currently working for</h3>
                        <p>{user.workplace}</p>

                    </div>
                    <br></br>
                    <div className="contact-card">
                        <h2><i>About Me</i></h2>
                        <h3>Education</h3>

                        <p>{user.education}</p>
                        
                        <h3>Work Experience</h3>
                        
                        <p>{user.workExperience}</p>
                        
                        <h3>Skills</h3>
                        
                        <p>{user.skills}</p>
                    </div>
                    
                </div>

                <div className="split right ">
                    <button className="chat-button">Send Message</button><br></br>
                    <br></br>
                    <div className="contact-card">
                        <h2><i>Contact Info</i></h2>
                        <p><b>Email:</b> {user.email}</p>
                        <p><b>Phone Number:</b> {user.phoneNumber}</p>
                        <p><b>Website:</b> <a href={user.website}>{user.website}</a> </p>
                    </div>

                </div>
            </div>
        </div>
    );
}

export default VisitProfile;