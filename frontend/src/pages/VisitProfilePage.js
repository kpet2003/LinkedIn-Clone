import React , { useState,useEffect } from "react";
import NavigationBar from './NavigationBar.js';
import userService from "../service/userService.js";
import { useParams, useNavigate } from 'react-router-dom';
import '../styling/Visit.css';
import '../styling/Profile.css';
import placeholder from '../icons/avatar.png';

//this page is the same as the profile page but without the possibility to change the user's data and 2 buttons, one to view the
//user's connections and on to send them a text. the second one only shows if the current user is connected to the users whose profile they
//are viewing

//function that shows a list of user's skills
function Skills({skills}) {
    return (
        <div>
            <ul>
                {skills.map(skill =>(
                    <li key = {skill.id}>
                        {skill.skill} 
                    </li>
                ))}
            </ul>
        </div>
    );
}

//function that shows a list of user's education
function Education({education}) {
    return (
        <div>
            <ul>
                {education.map(edu =>(
                    <li key = {edu.id}>
                        {edu.education} 
                    </li>
                ))}
            </ul>
        </div>
    );
}

//function that shows a list of user's work experience
function Experience({experience}) {
    return (
        <div>
            <ul>
                {experience.map(experience =>(
                    <li key = {experience.id}>
                        {experience.experience}
                    </li>
                ))}
            </ul>
        </div>
    );
}




function VisitProfile(){
    //initial state of user data
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

    const [user, setUser] = useState(initialState); //to keep user data
    const { id } = useParams(); //the id of the user whose profile the current user is viewing
    const [isAdmin,setAdmin] = useState(false); //to check if the user looking at the profile is the admin
    const [isConnected,setIsConnected] = useState(false); //to check if the two users are connected
    const [skills,setSkills] = useState([]); //list of user's skills
    const [education,setEducation] = useState([]); //list of user's education
    const [experience,setExperience] = useState([]); //list of user's work expereince
    const navigate = useNavigate(); //used later to navigate to differen pages

    //on page load
    useEffect(() => {
        //useEffect runs multiple times due to react strict mode so abort controllers are used so requests are made only once
        const cancelProfile = new AbortController();

        //function to get user's data from database
        const fetchUserData = async () => {
            try {
                const data = await userService.getProfile(id, cancelProfile); //get user's data
                setUser((user) => ({ //save it
                    ...user,
                    ...data
                }));
                const token =  userService.decodeToken(localStorage.getItem('jwt_token')); //decode token

                if(token.sub === 'admin@gmail.com') { //if user is the admin set the constant to true
                    setAdmin(true);
                }
            } catch (error) { //if error occurs
                console.error('Error fetching user data:', error); //write it to console
            }
        };

        const cancelConnected = new AbortController(); //another abort controller

        //function to check if current user is connected to this user
        const checkIfConnected = async() => {
            try {
                const token = localStorage.getItem('jwt_token');
                const response = await userService.checkConnection(token, id, cancelConnected); //check using the database
                setIsConnected(response); //save true or false depending on whether they are connected
            }   
            catch(error) { //if error occurs
                console.error("Error checking for connections: ",error); //write it to console
            }
        }

        const cancelSkills = new AbortController(); //another abort controller

        //function to get user's skills
        const fetchSkills = async() => {
            try{
                const response = await userService.getSkillsById(id, cancelSkills); //get skills from database
                setSkills(response); //save them
            }
            catch(error) { //if error occurs
                console.error("Error fetching the user's skills: ",error); //write it to console
            }
        }

        const cancelEdu = new AbortController(); //another abort controller

        //function to get user's education
        const fetchEducation = async() => {
            try{
                const response = await userService.getEducationById(id, cancelEdu); //get education from database
                setEducation(response); //save it
            }
            catch(error) { //if error occurs
                console.error("Error fetching the user's education: ",error); //write it to console
            }
        }

        const cancelExp = new AbortController(); //another abort controller

        //function to get user's work experience
        const fetchExperience = async() => {
            try{
                const response = await userService.getExperienceById(id, cancelExp); //get experience from database
                setExperience(response); //save it
            }
            catch(error) { //if error occurs
                console.error("Error fetching the user's experience: ",error); //write it to console
            }
        }

        //call functions to get the needed data
        fetchUserData();
        checkIfConnected();
        fetchSkills();
        fetchEducation();
        fetchExperience();

        return()=>{ //when function returns cancel all requests from other useEffect renders
            cancelProfile.abort();
            cancelConnected.abort();
            cancelSkills.abort();
            cancelEdu.abort();
            cancelExp.abort();
        }
    }, [id]);

    //function that takes user to a new page to view the other user's network
    const viewNetwork = (id) => {
        const link = document.createElement('a');
        link.href =  `/ViewNetwork/${id}`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    //function that takes the user to the messages page to start a chat with this user
    const startChat = (userId) => {
        navigate('/Messages', { state: { userId } });
    }

    const base64Image = user.profilePicture? `data:image/jpeg;base64,${user.profilePicture}`: `${placeholder}`; //if user has profile picture display it otherwise display default

    return(
        <div>
            {/* if user is not admin show navigation bar */}
            {!isAdmin && (<NavigationBar></NavigationBar>)}
            <br></br>
            <div>
                {/* show user's picture and name at the top */}
                <div className="banner-container" style={{ gap: isConnected ? '10%' : '0' }}>
                    <div className="banner">
                        <div className="profile-picture-container">
                            <img
                            src={base64Image}
                            alt="Profile"
                            className="profile-picture" 
                            />
                        </div>                    
                        <div>
                            <h1 className="username">{user.firstName} {user.lastName}</h1>
                        </div>
                    </div>
                    <div className="connected-buttons">
                        {/* if user is connceted to the other one show buttons. view network button shows for admin */}
                        { isConnected && (<button className="chat-button" style={{margin:0}} onClick={()=> startChat(id)}>Send Message</button>)}
                        { (isConnected || isAdmin) && (<button className="chat-button" style={{margin:0}} onClick={() => viewNetwork(id)}>View Network</button>)}
                    </div>
                </div>
                <br></br>
                <br></br>
                {/* display the user's data in different boxes like the profile */}
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
                    {((user.publicEducation && (!isConnected)) || isAdmin || isConnected) ? (
                    <>
                    <br></br>
                    <div className="card">
                        <div className="about-me-container">
                            <h2><i>Education</i></h2>
                        </div>
                        <Education education = {education}/>
                        </div>
                        </>
                    ) : null}
                    {((user.publicWork && (!isConnected)) || isAdmin || isConnected) ? (
                    <>
                        <br></br>
                        <div className="card">
                        <div className="about-me-container">
                            <h2><i>Work Experience</i></h2>
                            </div>
                       <Experience experience={experience}/>
                        </div>
                    </>
                    ) : null}
                    {((user.publicSkills && (!isConnected)) || isAdmin || isConnected)? (
                    <>
                        <br></br>
                        <div className="card">
                        <div className="about-me-container">
                        <h2><i>Skills</i></h2>
                       
                        </div>

                        <Skills skills = {skills}/>
                            

                    </div>
                    </>
                    ) : null }
                    <br></br>
                    <div className="card">
                        <h2><i>Contact Info</i></h2>
                        <p><b>Email:</b> {user.email}</p>
                        <p><b>Phone Number:</b> {user.phoneNumber}</p>
                      
                    </div>
                </div>
            </div>
        </div>
    );
}

export default VisitProfile;