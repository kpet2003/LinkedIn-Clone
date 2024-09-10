import React , { useState,useEffect } from "react";
import NavigationBar from './NavigationBar.js';
import userService from "../service/userService.js";
import { useParams } from 'react-router-dom';
import '../styling/Visit.css';
import '../styling/Profile.css';
import placeholder from '../icons/avatar.png';


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
    const [isAdmin,setAdmin] = useState(false);
    const [isConnected,setIsConnected] = useState(false);
    const [skills,setSkills] = useState([]);
    const [education,setEducation] = useState([]);
    const [experience,setExperience] = useState([]);
    console.log('userId is',id);

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const data = await userService.getProfile(id);
                setUser((user) => ({
                    ...user,
                    ...data
                }));
                const token =  userService.decodeToken(localStorage.getItem('jwt_token'));

                if(token.sub === 'admin@gmail.com') {
                    setAdmin(true);
                }
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };

        const checkIfConnected = async() => {
            try {
                const token = localStorage.getItem('jwt_token');
                const response = await userService.checkConnection(token,id);
                console.log('is user connected: ',response);
                setIsConnected(response)
            }   
            catch(error) {
                console.error("Error checking for connections: ",error);
            }
        }

        const fetchSkills = async() => {
            try{
               
                const response = await userService.getSkillsById(id);
                setSkills(response);
                console.log("skills are: ",response);
            }
            catch(error) {
                console.error("Error fetching the user's skills: ",error)
            }
        }

        const fetchEducation = async() => {
            try{
               
                const response = await userService.getEducationById(id);
                setEducation(response);
                console.log("education is: ",response);
            }
            catch(error) {
                console.error("Error fetching the user's education: ",error)
            }
        }

        const fetchExperience = async() => {
            try{
               
                const response = await userService.getExperienceById(id);
                setExperience(response);
                console.log("skills are: ",response);
            }
            catch(error) {
                console.error("Error fetching the user's experience: ",error)
            }
        }

        fetchUserData();
        checkIfConnected();
        fetchSkills();
        fetchEducation();
        fetchExperience();
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
            {!isAdmin && (<NavigationBar></NavigationBar>)}
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
                       { isConnected && (<button className="chat-button">Send Message</button>)}
                       { (isConnected || isAdmin) && (<button className="chat-button" onClick={() => viewNetwork(id)}>View Network</button>)}
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