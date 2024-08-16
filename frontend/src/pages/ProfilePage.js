import "../Profile.css";
import penIcon from "../pen.png";
import React, { useState, useEffect } from 'react';
import NavigationBar from './HomePage.js'
import userService from "../service/userService.js";
import Popup from 'reactjs-popup';
import ToggleSwitch from "../components/ToggleSwitch.js";
import placeholder from '../avatar.png'



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
        publicEducation: '',
        workTitle: '',
        workplace: '',
        website: ''
    };

    const [user, setUser] = useState(initialState);
    const [selectedFile, setSelectedFile] = useState(null);
    const [changedField, setChangedField] = useState('');


    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const data = await userService.getUserData(localStorage.getItem('jwt_token'));
                setUser((user) => ({
                    ...user,
                    ...data
                }));
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };

        fetchUserData();
    }, []);

    const handleChange = (event) => {
        setChangedField('profilePicture');
        const file = event.target.files[0];
        setSelectedFile(file);
    };

    const handleChangedText = (event) => {
        const { id, value } = event.target;
        setChangedField(id);
        setUser({
        ...user,
        [id]: value,
        });
    }
    
    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            if(changedField === 'profilePicture'){
                const formData = new FormData();
                formData.append('profilePicture', selectedFile);
                formData.append('token', localStorage.getItem('jwt_token'));
                await userService.changeProfilePicture(formData);
                alert('Profile picture changed successfully');
            }
            else {
                if(changedField === 'education'){
                    const data = {
                        token: localStorage.getItem('jwt_token'),
                        info: user.education
                    }
                    await userService.changeEducation(data);
                    alert('Education info changed successfully');
                }
                else if(changedField === 'workExperience'){
                    const data = {
                        token: localStorage.getItem('jwt_token'),
                        info: user.workExperience
                    }
                    await userService.changeWork(data);
                    alert('Work experience changed successfully');
                }
                else if(changedField === 'skills'){
                    const data = {
                        token: localStorage.getItem('jwt_token'),
                        info: user.skills
                    }
                    await userService.changeSkills(data);
                    alert('Skills changed successfully');
                }
                else{
                    alert('Nothing was changed');
                    return;
                }
            }
        
            const updatedUser = await userService.getUserData(localStorage.getItem('jwt_token'));
            setUser((user) => ({
                ...user,
                ...updatedUser,
            }));
        } catch (error) {
            console.error('There was an error changing data', error);
            alert('There was an error changing your data');
        }
        setChangedField('');
    };

    const handleBool = async (event) => {
        event.preventDefault();
        const id = event.target.id;
        console.log('id is ', id);
        const data={
            token: localStorage.getItem('jwt_token'),
        };
        try{
            if(id === 'edu'){
                console.log('changing education');
                await userService.changeEduState(data);
            }
            else if(id === 'work'){
                await userService.changeWorkState(data);
            }
            else{
                await userService.changeSkillsState(data);
            }
        } catch(error){
            console.error('Could not change bool', error);
            alert('Could not change the state of your information');
        }
    }

    const base64Image = user.profilePicture? `data:image/jpeg;base64,${user.profilePicture}`: `${placeholder}`;

    return (
        <div>
            <div className="banner">
                <div className="pic-edit">
                <div className="profile-picture-container">
                    <img
                    src={base64Image}
                    alt="Profile"
                    className="profile-picture"
                    />
                </div>
                <Popup
                    trigger={
                    <div className="pen-icon" title="Edit Profile Picture">
                        <img src={penIcon} alt="Pen" className="pen-icon" />
                    </div>
                    }
                    modal
                    closeOnDocumentClick
                    className="modal-content"
                >
                    {(close) => (
                    <div className="modal-background">
                        <span className="close" onClick={close}>
                        &times;
                        </span>
                        <h2>Edit Profile Picture</h2>
                        <form onSubmit={handleSubmit}>
                        <input type="file" className="file-input" onChange={handleChange} id="profilePicture" />
                        <input type="submit" value="Save" className="save-button" />
                        </form>
                    </div>
                    )}
                </Popup>
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
                        <div className="about-me-container">
                            <Popup
                                trigger={
                                <div className="pen-icon" title="Edit Education Info">
                                    <img src={penIcon} alt="Pen" className="pen-icon" />
                                </div>
                                }
                                modal
                                closeOnDocumentClick
                                className="modal-content"
                            >
                                {(close) => (
                                <div className="modal-background">
                                    <span className="close" onClick={close}>
                                    &times;
                                    </span>
                                    <h2>Edit Education Info</h2>
                                    <form onSubmit={handleSubmit}>
                                    <input type="text" className="file-input" onChange={handleChangedText}  id="education"/>
                                    <input type="submit" value="Save" className="save-button" />
                                    </form>
                                </div>
                                )}
                            </Popup>
                            <h3>Education</h3>
                            <ToggleSwitch className="switch" onChange={handleBool} checked={user.publicEducation} id="edu"></ToggleSwitch>
                        </div>
                        <p>{user.education}</p>
                        <div className="about-me-container">
                            <Popup
                                trigger={
                                <div className="pen-icon" title="Edit Work Experience">
                                    <img src={penIcon} alt="Pen" className="pen-icon" />
                                </div>
                                }
                                modal
                                closeOnDocumentClick
                                className="modal-content"
                            >
                                {(close) => (
                                <div className="modal-background">
                                    <span className="close" onClick={close}>
                                    &times;
                                    </span>
                                    <h2>Edit Work Experience</h2>
                                    <form onSubmit={handleSubmit}>
                                    <input type="text" className="file-input" onChange={handleChangedText} id="workExperience" />
                                    <input type="submit" value="Save" className="save-button" />
                                    </form>
                                </div>
                                )}
                            </Popup>
                            <h3>Work Experience</h3>
                            <ToggleSwitch className="switch" onChange={handleBool} checked={user.publicWork} id="work"></ToggleSwitch>
                            </div>
                        <p>{user.workExperience}</p>
                            
                        <div className="about-me-container">
                        <Popup
                            trigger={
                            <div className="pen-icon" title="Edit Skills">
                                <img src={penIcon} alt="Pen" className="pen-icon" />
                            </div>
                            }
                            modal
                            closeOnDocumentClick
                            className="modal-content"
                        >
                            {(close) => (
                            <div className="modal-background">
                                <span className="close" onClick={close}>
                                &times;
                                </span>
                                <h2>Edit Skills</h2>
                                <form onSubmit={handleSubmit}>
                                <input type="text" className="file-input" onChange={handleChangedText} id="skills"/>
                                <input type="submit" value="Save" className="save-button" />
                                </form>
                            </div>
                            )}
                        </Popup>
                        <h3>Skills</h3>
                        <ToggleSwitch className="switch" onChange={handleBool} checked={user.publicSkills} id="skills"></ToggleSwitch>
                        </div>

                        <p>{user.skills}</p>
                        
                        

                    </div>
                    
                </div>

                <div className="split right ">
                    
                    <div className="contact-card">
                        <h2><i>Contact Info</i></h2>
                        <p><b>Email:</b> {user.email}</p>
                        <p><b>Phone Number:</b> {user.phoneNumber}</p>
                        <p><b>Website:</b> <a href={user.website}>{user.website}</a> </p>
                    </div>

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