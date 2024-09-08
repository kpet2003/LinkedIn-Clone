
import '../styling/HomePage.css';
import '../styling/Jobs.css'
import React, { useEffect, useState,Suspense } from 'react';
import userService from "../service/userService.js";
import jobService from "../service/jobService.js";
import avatar from '../icons/avatar.png';
import ArticleService from '../service/articleService.js';
import NavigationBar from './NavigationBar.js';
import {useNavigate} from "react-router-dom";
import Popup from 'reactjs-popup';



function Jobs() {

    const initialState = {
        title: '',
        desc: '',
        skills: []
    };

    const [jobs, setJobs] = useState([]);
    const [newJob, setNewJob] = useState(initialState);
    const [newSkill, setNewSkill] = useState('');
    const [showJobDesc, setShowJobDesc] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await jobService.getJobs(localStorage.getItem('jwt_token'));
                setJobs(data);
                console.log(jobs);
            } 
            catch (error) {
                console.error("There was an error getting the available jobs", error);
            }
        }
        fetchData();
    },[]);

    const handleChange = (event) => {
        setNewJob({
            ...newJob,
            [event.target.id]: event.target.value,
        });
    };

    const handleSkillInput = (event) => {
        setNewSkill(event.target.value);
    };

    const addSkill = () => {
        if (newSkill.trim() !== '') {  // Ensure skill input is not empty
          setNewJob((prevState) => ({
            ...prevState,
            skills: [...prevState.skills, newSkill]
          }));
          setNewSkill('');  // Clear the input field after adding
        }
      };

    const handleSubmit = async(event) => {
        event.preventDefault();

        const data={
            authorToken: localStorage.getItem('jwt_token'),
            jobTitle: newJob.title,
            jobDesc: newJob.desc,
            jobSkills: newJob.skills
        };
        try {
            const response = await jobService.newJob(data);
            alert('Job submitted successfully');
            reset();
        } catch (error) {
            console.log('Error posting: ', error);
            alert('There was an error posting the job');
        }
    }

    const reset = ()=>{
        setNewJob(initialState);
        setNewSkill('');
    }

    const toggleDescription = (i) => {
        // Toggle the description for the clicked job
        if (showJobDesc === i) {
            setShowJobDesc(null);  // Close the description if clicked again
        } else {
            setShowJobDesc(i);  // Show the description for the clicked job
        }
    };

    const apply = async(i)=>{
        try {
            await jobService.applyToJob(i, localStorage.getItem('jwt_token'));
            alert("Applied successfully");
            
        } catch (error) {
            console.error("Error applying for job:", error);
            alert("There was an error applying for the job");
        }
    }

    return (
        <div>
            <Popup trigger={
                <div style={{textAlign: 'right', marginRight: '20%'}}>
                <button className='save-button'>Post a Job</button>
                </div>
            } modal closeOnDocumentClick className="modal-content create-job-container" onClose={reset}>
                {(close) => (
                    <div className='modal-background create-job-container'>
                        <span className="close" onClick={close}>
                        &times;
                        </span><br></br>
                        <h2>Post a Job</h2>
                        <form onSubmit={handleSubmit}>
                        <label style={{ display: 'block', textAlign: 'left' }}>Job Title:</label><br></br>
                        <textarea className='insert-text' style={{width: 250}} onChange={handleChange} id='title'></textarea><br></br><br></br>
                        <label style={{ display: 'block', textAlign: 'left' }}>Job Description:</label><br></br>
                        <textarea className='insert-text about' style={{width: 250}} onChange={handleChange} id='desc'></textarea><br></br><br></br>
                        <label style={{ display: 'block', textAlign: 'left' }}>Required Skills: </label><br></br>
                        <input 
                            type="text" 
                            value={newSkill} 
                            onChange={handleSkillInput} // Controlled input for skill
                            placeholder="Add a skill" 
                            style={{borderRadius: 5, border: '1px solid gray', height: 25}}
                            />
                            <span onClick={addSkill} style={{ marginLeft: '10px', cursor: 'pointer' }}>&#43;</span>
                            
                            <br />
                            <ul>
                            {newJob.skills.map((skill, index) => (
                                <li key={index} style={{textAlign:'left'}}>{skill}</li>
                            ))}
                            </ul>
                        <input type="submit" value="Post" className="save-button"  />
                        </form>
                    </div>
                    )}
            </Popup>
        
        <br></br>
            {
                jobs.map((job,i)=>(
                    <>
                    <div className='job-container' key={job.id}>
                        <h3 className='job-header' onClick={() => toggleDescription(i)}>{job.title}</h3>
                        <p className='author'>Posted by {job.author}</p>
                        {showJobDesc === i && (
                            <div style={{ marginLeft: '2%' }}>
                                <p>{job.desc}</p>
                                <button className='save-button apply-button' onClick={() => apply(job.jobId)}>Apply</button>
                            </div>
                        )}
                        <span className='footer'>
                            {new Date(job.date).toLocaleDateString()}
                            &nbsp;
                            {new Date(job.date).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                        </span>                    
                    </div>
                    <br></br>
                    </>
                )

            )}

        
        </div>
    )
}

function JobPage() {
    return (
        <div>
            <NavigationBar></NavigationBar>
            <br></br>
            <Jobs></Jobs>
        </div>
     
    );
}

export default JobPage;