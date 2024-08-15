import "../Profile.css";
import penIcon from "../pen.png";
import React, { useState, useEffect } from 'react';
import NavigationBar from './HomePage.js'
import userService from "../service/userService.js";
import Popup from 'reactjs-popup';
import ToggleSwitch from "../components/ToggleSwitch.js";



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

    const base64Image = `data:image/jpeg;base64,${user.profilePicture}`;

    return (
        <div>
            <div className="banner">
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
                <ToggleSwitch className="switch" onChange={handleBool} checked={user.publicEducation} id="edu"></ToggleSwitch>
                </div>
                <p>{user.education}</p>

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
                <div className="header-container">
                <h3 className="title">Work Experience</h3>
                <ToggleSwitch className="switch" onChange={handleBool} checked={user.publicWork} id="work"></ToggleSwitch>
                </div>
                <p>{user.workExperience}</p>
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
                <div className="header-container">
                <h3 className="title">Skills</h3>
                <ToggleSwitch className="switch" onChange={handleBool} checked={user.publicSkills} id="skills"></ToggleSwitch>
                </div>
                <p>{user.skills}</p>
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