import React from "react";
import NavigationBar from './NavigationBar.js';
import userService from "../service/userService.js";
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import '../styling/Visit.css';
import '../styling/Profile.css';
import placeholder from '../icons/avatar.png';

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

    const viewNetwork = (id) => {
        console.log(id);
        const link = document.createElement('a');
        link.href =  `/ViewNetwork/${id}`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);

    }

    const base64Image = user.profilePicture? `data:image/jpeg;base64,${user.profilePicture}`: `${placeholder}`;

    return(
        <div>
            <NavigationBar></NavigationBar>
            <br></br>
            <div>
                <div className="banner">
                    <div className="start-chat-container">
                        <div className="profile-picture-container">
                            <img
                            src={base64Image}
                            alt="Profile"
                            className="profile-picture"
                            />
                        </div>
                        <button className="chat-button">Send Message</button>
                        <button className="chat-button" onClick={() => viewNetwork(id)}>View Network</button>
                    </div>
                    
                    <div>
                        <h1 className="username">{user.firstName} {user.lastName}</h1>
                    </div>
                </div>
                <br></br>
                <br></br>
                <div className="cards-container">
                    <div className="card">
                        <h2>
                            <i>Work</i>
                        </h2>
                        <div className="about-me-container">
                        <h3>Currently working as a(n)</h3>
                        </div>
                        <p>{user.workTitle}</p>
                        <div className="about-me-container">
                        <h3>For</h3>
                        </div>
                        <p>{user.workplace}</p>

                    </div>
                    {user.publicEducation ? (
                    <>
                    <br></br>
                    <div className="card">
                        <div className="about-me-container">
                            <h2><i>Education</i></h2>
                        </div>
                        <p>{user.education}</p>
                        </div>
                        </>
                    ) : null}
                    {user.publicWork ? (
                    <>
                        <br></br>
                        <div className="card">
                        <div className="about-me-container">
                            <h2><i>Work Experience</i></h2>
                            </div>
                        <p>{user.workExperience}</p>
                        </div>
                    </>
                    ) : null}
                    {user.publicSkills ? (
                    <>
                        <br></br>
                        <div className="card">
                        <div className="about-me-container">
                        <h2><i>Skills</i></h2>
                        </div>

                        <p>{user.skills}</p>
                            

                    </div>
                    </>
                    ) : null }
                    <br></br>
                    <div className="card">
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