import "../styling/Profile.css";
import "../styling/Visit.css";
import penIcon from "../icons/pen.png";
import React, { useState, useEffect } from 'react';
import NavigationBar from './NavigationBar.js';
import userService from "../service/userService.js";
import Popup from 'reactjs-popup';
import ToggleSwitch from "../components/ToggleSwitch.js";
import placeholder from '../icons/avatar.png'
import trash from '../icons/trash.png';


function Skills({skills,deleteSkill}) {

    return (
        <div>
            <ul>
                {skills.map(skill =>(
                    <li key = {skill.id}>
                        {skill.skill} <img src= {trash} alt="trash" style={{ cursor: 'pointer', marginLeft: '8px' }} onClick={()=>deleteSkill(skill.id)} />
                    </li>
                ))}
            </ul>
        </div>
    );
}

function Education({education,deleteEdu}) {

    return (
        <div>
            <ul>
                {education.map(edu =>(
                    <li key = {edu.id}>
                        {edu.education} <img src= {trash} alt="trash" style={{ cursor: 'pointer', marginLeft: '8px' }} onClick={()=>deleteEdu(edu.id)} />
                    </li>
                ))}
            </ul>
        </div>
    );
}

function Experience({experience,deleteExperience}) {
    return (
        <div>
            <ul>
                {experience.map(experience =>(
                    <li key = {experience.id}>
                        {experience.experience} <img src= {trash} alt="trash" style={{ cursor: 'pointer', marginLeft: '8px' }} onClick={()=>deleteExperience(experience.id)} />
                    </li>
                ))}
            </ul>
        </div>
    );
}



function Pfp(){
    const initialState = {
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        profilePicture: '',
        resume: null,
        workExperience: '',
        publicWork: '',
        publicSkills: '',
        publicEducation: '',
        workTitle: '',
        workplace: '',
        education:'',
        skills:'',
       
    };

    const [user, setUser] = useState(initialState);
    const [selectedFile, setSelectedFile] = useState(null);
    const [changedField, setChangedField] = useState('');
    const [skills,setSkills] = useState([]);
    const [education,setEducation] = useState([]);
    const [experience,setExperience] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [user, education, experience,skills] = await Promise.all([
                    userService.getUserData(localStorage.getItem('jwt_token')),
                    userService.getEducation(localStorage.getItem('jwt_token')),
                    userService.getExperience(localStorage.getItem('jwt_token')),
                    userService.getSkills(localStorage.getItem('jwt_token'))
                ]);
                setUser(user);
                setEducation(education);
                setExperience(experience);
                setSkills(skills);
                
            } 
            catch (error) {
                console.error("There was an error getting the user list", error);
            }
        }
        fetchData();
    },[]);


    const deleteSkill = async(skill_id) => {
        try {
            await userService.removeSkill(skill_id,localStorage.getItem('jwt_token'));
            const response = await userService.getSkills(localStorage.getItem('jwt_token'));
            setSkills(response);
        }
        catch(error) {
            console.error('error deleting the skill: ',error);
        }
    }

    const deleteEducation = async(edu_id) =>{
        try {
            await userService.removeEdu(edu_id,localStorage.getItem('jwt_token'));
            const response = await userService.getEducation(localStorage.getItem('jwt_token'));
            setEducation(response);
        }
        catch(error) {
            console.error('error deleting the education: ',error);
        }
    }

    const deleteExperience = async(exp_id) => {
        try {
            await userService.removeExp(exp_id,localStorage.getItem('jwt_token'));
            const response = await userService.getExperience(localStorage.getItem('jwt_token'));
            setExperience(response);
        }
        catch(error) {
            console.error('error deleting the experience: ',error);
        }
    }

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
                        info:user.education
                    }
                    console.log('user education is: ',user.education)
                    await userService.changeEducation(data);
                    alert('Education info changed successfully');
                    const response = await userService.getEducation(localStorage.getItem('jwt_token'));
                    setEducation(response);
                }
                else if(changedField === 'workExperience'){
                    const data = {
                        token: localStorage.getItem('jwt_token'),
                        info: user.workExperience
                    }
                    await userService.changeWork(data);
                    alert('Work experience changed successfully');
                    const response = await userService.getExperience(localStorage.getItem('jwt_token'));
                    setExperience(response);
                }
                else if(changedField === 'skills'){
                    const data = {
                        token: localStorage.getItem('jwt_token'),
                        info: user.skills
                    }
                    await userService.changeSkills(data);
                    alert('Skills changed successfully');
                    const response = await userService.getSkills(localStorage.getItem('jwt_token'));
                    setSkills(response);
                }
                else if(changedField === 'workTitle'){
                    const data = {
                        token: localStorage.getItem('jwt_token'),
                        info: user.workTitle
                    }
                    await userService.changeWorkTitle(data);
                    alert('Work title changed successfully');
                }
                else if(changedField === 'workplace'){
                    const data = {
                        token: localStorage.getItem('jwt_token'),
                        info: user.workplace
                    }
                    await userService.changeWorkplace(data);
                    alert('Workplace changed successfully');
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
            const updatedUser = await userService.getUserData(localStorage.getItem('jwt_token'));
            setUser((user) => ({
                ...user,
                ...updatedUser,
            }));
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
                <div className="profile-picture-container" style={{marginLeft: '20px'}}>
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
                        </span><br></br>
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
                    <Popup
                        trigger={
                        <div className="pen-icon" title="Edit Work Title">
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
                            </span><br></br>
                            <h2>Edit Work Title</h2>
                            <form onSubmit={handleSubmit}>
                            <input type="text" className="file-input" onChange={handleChangedText} id="workTitle" style={{ outline: 'none' }} />
                            <input type="submit" value="Save" className="save-button" />
                            </form>
                        </div>
                        )}
                    </Popup>
                    <h3>Currently working as a(n)</h3>
                    </div>
                    <p>{user.workTitle}</p>
                    <div className="about-me-container">
                    <Popup
                        trigger={
                        <div className="pen-icon" title="Edit Workplace">
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
                            </span><br></br>
                            <h2>Edit Workplace</h2>
                            <form onSubmit={handleSubmit}>
                            <input type="text" className="file-input" onChange={handleChangedText} id="workplace" style={{ outline: 'none' }}/>
                            <input type="submit" value="Save" className="save-button" />
                            </form>
                        </div>
                        )}
                    </Popup>
                    <h3>For</h3>
                    </div>
                    <p>{user.workplace}</p>

                </div>
                <br></br>
                <div className="card">
                    <div className="about-me-container">
                        <Popup
                            trigger={
                            <div className="pen-icon" title="Add Education">
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
                                </span><br></br>
                                <h2>Add Education</h2>
                                <form onSubmit={handleSubmit}>
                                    <input type="text" className="file-input" onChange={handleChangedText} id="education" value={user.education} style={{ outline: 'none' }}/>
                                    <input type="submit" value="Save" className="save-button" />
                                </form>
                            </div>
                            )}
                        </Popup>
                        <h2><i>Education</i></h2>
                        <ToggleSwitch className="switch" onChange={handleBool} checked={user.publicEducation} id="edu"></ToggleSwitch>
                    </div>
                    <Education education = {education} deleteEdu={deleteEducation}/>
                    </div>
                    <br></br>
                    <div className="card">
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
                                </span><br></br>
                                <h2>Edit Work Experience</h2>
                                <form onSubmit={handleSubmit}>
                                <input type="text" className="file-input" onChange={handleChangedText} id="workExperience" style={{ outline: 'none' }}/>
                                <input type="submit" value="Save" className="save-button" />
                                </form>
                            </div>
                            )}
                        </Popup>
                        <h2><i>Work Experience</i></h2>
                        <ToggleSwitch className="switch" onChange={handleBool} checked={user.publicWork} id="work"></ToggleSwitch>
                        </div>
                        < Experience experience={experience} deleteExperience={deleteExperience}    />
                    </div>
                    <br></br>
                    <div className="card">
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
                            </span><br></br>
                            <h2> Add Skill</h2>
                            <form onSubmit={handleSubmit}>
                            <input type="text" className="file-input" onChange={handleChangedText} id="skills"style={{ outline: 'none' }}/>
                            <input type="submit" value="Save" className="save-button" />
                            </form>
                        </div>
                        )}
                    </Popup>
                    <h2><i>Skills</i></h2>
                    <ToggleSwitch className="switch" onChange={handleBool} checked={user.publicSkills} id="skills"></ToggleSwitch>
                    </div>

                   <Skills skills={skills} deleteSkill={deleteSkill}/>
                        

                </div>
                <br></br>
                <div className="card">
                    <h2><i>Contact Info</i></h2>
                    <p><b>Email:</b> {user.email}</p>
                    <p><b>Phone Number:</b> {user.phoneNumber}</p>
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