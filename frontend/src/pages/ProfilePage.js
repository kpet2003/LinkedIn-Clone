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

//function that shows the user's skills and an icon to delete them next to each skill
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

//function that shows the user's education in a list and an icon next to each point to delete it
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

//function that shows the user's experience in a list and an icon next to each point to delete it
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
    //initial state for the user's data
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

    const [user, setUser] = useState(initialState); //to keep user data
    const [selectedFile, setSelectedFile] = useState(null); //to keep file selected for the profile picture
    const [changedField, setChangedField] = useState(''); //to keep which profile field was changed by the user
    const [skills,setSkills] = useState([]); //to keep user's skills
    const [education,setEducation] = useState([]); //to keep user's education
    const [experience,setExperience] = useState([]); //to keep user's experience

    //on page load
    useEffect(() => {
        //useEffect runs multiple times due to react strict mode, use abort controllers so requests happen only once
        const cancelUser = new AbortController();
        const cancelEdu = new AbortController();
        const cancelExp = new AbortController();
        const cancelSkills = new AbortController();

        //function to get the user's data
        const fetchData = async () => {
            try {
                const [user, education, experience,skills] = await Promise.all([ //wait for all requests to be made
                    userService.getUserData(localStorage.getItem('jwt_token'), cancelUser), //get user's data
                    userService.getEducation(localStorage.getItem('jwt_token'), cancelEdu), //get user's education
                    userService.getExperience(localStorage.getItem('jwt_token'), cancelExp), //get user's experience
                    userService.getSkills(localStorage.getItem('jwt_token'), cancelSkills) //get user's skills
                ]);
                //save the data retrieved in the corrsponding constant
                setUser(user);
                setEducation(education);
                setExperience(experience);
                setSkills(skills);
                
            } 
            catch (error) { //if error occurs
                console.error("There was an error getting the user list", error); //write it to console
            }
        }

        fetchData();//get user's data

        return()=>{ //after function completes stop any requests from happening again
            cancelUser.abort();
            cancelEdu.abort();
            cancelExp.abort();
            cancelSkills.abort();
        }
    },[]);

    //function to delete a skill
    const deleteSkill = async(skill_id) => {
        try {
            await userService.removeSkill(skill_id,localStorage.getItem('jwt_token')); //remove skill from database
            const response = await userService.getSkills(localStorage.getItem('jwt_token')); //get the updated list of skills
            setSkills(response); //save the new skills
        }
        catch(error) { //if error occurs
            console.error('error deleting the skill: ',error); //write it to console
        }
    }

    //function to delete education point
    const deleteEducation = async(edu_id) =>{
        try {
            await userService.removeEdu(edu_id,localStorage.getItem('jwt_token')); //remove from database
            const response = await userService.getEducation(localStorage.getItem('jwt_token')); //get the updated list of education points
            setEducation(response); //save them
        }
        catch(error) { //if error occurs
            console.error('error deleting the education: ',error); //write it to console
        }
    }

    //function to delete experience point
    const deleteExperience = async(exp_id) => {
        try {
            await userService.removeExp(exp_id,localStorage.getItem('jwt_token')); //remove from database
            const response = await userService.getExperience(localStorage.getItem('jwt_token')); //get the updated list of experience points
            setExperience(response); //save them
        }
        catch(error) { //if error occurs
            console.error('error deleting the experience: ',error); //write it to console
        }
    }

    //handle new profile picture being chosen
    const handleChange = (event) => {
        setChangedField('profilePicture');
        const file = event.target.files[0];
        setSelectedFile(file);
    };

    //handle user changing a field in their profile
    const handleChangedText = (event) => {
        const { id, value } = event.target;
        setChangedField(id);
        setUser({
        ...user,
        [id]: value,
        });
    }
    
    //function to handle submitting a change to the user's profile
    const handleSubmit = async (event) => {
        event.preventDefault(); //prevent default get request from happening
        try {
            if(changedField === 'profilePicture'){ //if profile picture was changed
                const formData = new FormData(); //prepare data to be sent to database
                formData.append('profilePicture', selectedFile);
                formData.append('token', localStorage.getItem('jwt_token'));
                await userService.changeProfilePicture(formData); //change profile picture in database
                alert('Profile picture changed successfully'); //message to user
            }
            else {
                if(changedField === 'education'){ //if education was changed
                    const data = { //prepare data to be sent
                        token: localStorage.getItem('jwt_token'),
                        info:user.education
                    }

                    await userService.changeEducation(data); //change education in database
                    alert('Education info changed successfully'); //message to user
                    const response = await userService.getEducation(localStorage.getItem('jwt_token')); //get updated education
                    setEducation(response); //save it
                }
                else if(changedField === 'workExperience'){ //if experience was changed
                    const data = { //prepare data to be sent
                        token: localStorage.getItem('jwt_token'),
                        info: user.workExperience
                    }
                    await userService.changeWork(data); //change experience in database
                    alert('Work experience changed successfully'); //message to user
                    const response = await userService.getExperience(localStorage.getItem('jwt_token')); //get updated experience
                    setExperience(response); //save it
                }
                else if(changedField === 'skills'){ //if skills were changed
                    const data = { //prepare data to be sent
                        token: localStorage.getItem('jwt_token'),
                        info: user.skills
                    }
                    await userService.changeSkills(data); //change skills in database
                    alert('Skills changed successfully'); //message to user
                    const response = await userService.getSkills(localStorage.getItem('jwt_token'),null); //get updated skills
                    setSkills(response); //save them
                }
                else if(changedField === 'workTitle'){ //if work title was changed
                    const data = { //prepare data to be sent
                        token: localStorage.getItem('jwt_token'),
                        info: user.workTitle
                    }
                    await userService.changeWorkTitle(data); //change work title in database
                    alert('Work title changed successfully'); //message to user
                }
                else if(changedField === 'workplace'){ //if workplace was changed
                    const data = { //prepare data to be sent
                        token: localStorage.getItem('jwt_token'),
                        info: user.workplace
                    }
                    await userService.changeWorkplace(data); //change workplace in database
                    alert('Workplace changed successfully'); //message to user
                }
                else{
                    alert('Nothing was changed'); //otherwise tell user nothing has been changed
                    return;
                }
            }
        
            const updatedUser = await userService.getUserData(localStorage.getItem('jwt_token')); //get updated user data
            setUser((user) => ({ //save it
                ...user,
                ...updatedUser,
            }));
        } catch (error) { //if error occurs
            console.error('There was an error changing data', error); //write to console
            alert('There was an error changing your data'); //message to user
        }
        setChangedField(''); //reset the input of the changed field
    };

    //function to handle the user making information private/public
    const handleBool = async (event) => {
        event.preventDefault(); //prevent the default get request from happening
        const id = event.target.id;
        const data={ //data to be sent
            token: localStorage.getItem('jwt_token'),
        };
        try{
            if(id === 'edu'){ //if education state changed
                await userService.changeEduState(data); //change it in the database
            }
            else if(id === 'work'){ //if work state changed
                await userService.changeWorkState(data); //change it in the database
            }
            else{
                await userService.changeSkillsState(data);//otherwise skills state has changed, change it
            }
            const updatedUser = await userService.getUserData(localStorage.getItem('jwt_token')); //get updated user data
            setUser((user) => ({ //save user data
                ...user,
                ...updatedUser,
            }));
        } catch(error){ //if error occurs
            console.error('Could not change bool', error); //write to console
            alert('Could not change the state of your information'); //message to user
        }
    }

    const base64Image = user.profilePicture? `data:image/jpeg;base64,${user.profilePicture}`: `${placeholder}`; //if user has profile picture display it otherwise display default

    return (
        <div>
            {/*on top goes the user's picture with their name underneath */}
            <div className="banner">
                <div className="pic-edit">
                <div className="profile-picture-container" style={{marginLeft: '20px'}}>
                    <img
                    src={base64Image}
                    alt="Profile"
                    className="profile-picture"
                    />
                </div>
                {/* popup triggered by a pen icon next to profile picture to change it */}
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
            {/* boxes with different types of user information and a pen icon popup to change each type of info */}
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
                {/* underneath the user info a contact information card */}
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