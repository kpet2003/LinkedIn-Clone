import "../Profile.css";
import penIcon from "../pen.png";
import React, { useState, useEffect } from 'react';
import NavigationBar from './HomePage.js'
import userService from "../service/userService.js";


function Pfp(){
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

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const data = await userService.getUserData(localStorage.getItem('userID'));
                setUser((user) => ({
                    ...user,
                    ...data // merge fetched data into the existing user state
                }));
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };

        fetchUserData();
    }, []);

    console.log(user.profilePicture);

    const base64Image = `data:image/jpeg;base64,${user.profilePicture}`;

    return(
        <div className="banner">
            <div className="profile-picture-container">
                <img
                    src={base64Image}
                    alt="Profile"
                    className="profile-picture"
                />
            </div>
            <div
                className="pen-icon"
                title="Edit Profile Picture"
                // onClick={() => handleEditClick("Profile Picture")}
            >
                <img
                    src={penIcon}
                    alt="Pen"
                    className="pen-icon"
                />
            </div>
            <div>
                <h1 className="username">{user.firstName} {user.lastName}</h1>
            </div>
        </div>
    );
}

function Profile(){
    return(
        <div>
            <NavigationBar></NavigationBar>
            <br></br>
            <Pfp></Pfp>
        </div>
    );
}

export default Profile;