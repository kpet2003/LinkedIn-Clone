
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

    const [myJobs, setMyJobs] = useState([]);
    const [connectionsJobs, setConnectionsJobs] = useState([]);
    const [othersJobs, setOthersJobs] = useState([]);
    const [newJob, setNewJob] = useState(initialState);
    const [newSkill, setNewSkill] = useState('');
    const [showMyJobDesc, setShowMyJobDesc] = useState(null);
    const [showConnJobDesc, setShowConnJobDesc] = useState(null);
    const [showOtherJobDesc, setShowOtherJobDesc] = useState(null);
    const [showAppliedJobDesc, setShowAppliedJobDesc] = useState(null);
    const [jobsApplied, setJobsApplied] = useState([]);
    const [showApplicants, setshowApplicants] = useState(null);
    const [applicants, setApplicants] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [myJobs, connectionsJobs, othersJobs] = await Promise.all([
                    jobService.getMyJobs(localStorage.getItem('jwt_token')),
                    jobService.getConnectionJobs(localStorage.getItem('jwt_token')),
                    jobService.getOtherJobs(localStorage.getItem('jwt_token'))
                ]);
                setMyJobs(myJobs);
                setConnectionsJobs(connectionsJobs);
                setOthersJobs(othersJobs);
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

    const toggleMyDescription = (i) => {
        if (showMyJobDesc === i) {
            setShowMyJobDesc(null);
        } else {
            setShowMyJobDesc(i);
        }
    };

    const toggleConnDescription = (i,job_id) => {
        if (showConnJobDesc === i) {
            setShowConnJobDesc(null);
        } else {
            setShowConnJobDesc(i);
            try {
                jobService.addView(job_id,localStorage.getItem('jwt_token'));
            }
            catch(error) {
                console.log('error adding view to job: ',error);
            }
        }
    };

    const toggleOtherDescription = (i,job_id) => {
        if (showOtherJobDesc === i) {
            setShowOtherJobDesc(null);
        } else {
            setShowOtherJobDesc(i);
            try {
                jobService.addView(job_id,localStorage.getItem('jwt_token'));
            }
            catch(error) {
                console.log('error adding view to job: ',error);
            }
        }
    };

    const toggleAppliedDescription = (i) => {
        if (showAppliedJobDesc === i) {
            setShowAppliedJobDesc(null);
        } else {
            setShowAppliedJobDesc(i);
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

    const getJobsApplied = async()=>{
        try{
        const response = await jobService.getJobsApplied(localStorage.getItem('jwt_token'));
        setJobsApplied(response);
        }catch(error){
            console.error("Error getting jobs applied:",error);
            alert("Could not get the jobs you have applied for")
        }
    }

    const getApplicants = async(i)=>{
        try {
            if (showApplicants === i) {
                setshowApplicants(null);
            } else {
                const data = await jobService.getJobApplicants(i, localStorage.getItem('jwt_token'));
                setApplicants(data);
                setshowApplicants(i);
            }
            
        } catch (error) {
            console.error("Error getting applicants:", error);
            alert("There was an error getting the applicants");
        }
    }

    return (
        <div>
            <div className='buttons-container'>
            <Popup trigger={
                <div>
                <button className='save-button job-button' onClick={getJobsApplied}>Jobs Applied For</button>
                </div>
            } modal closeOnDocumentClick className="modal-content">
                {(close) => (
                    <div className='modal-background'>
                        <span className="close" onClick={close}>
                        &times;
                        </span><br></br><br></br>
                        <div className='view-jobs'>
                    {
                        jobsApplied.map((job,i)=>(
                            <>
                            <div className='in-view' key={job.id}>
                                <h3 className='job-header' onClick={() => toggleAppliedDescription(i)}>{job.title}</h3>
                                <p className='author'>Posted by {job.author}</p>
                                {showAppliedJobDesc === i && (
                                    <div style={{ marginLeft: '2%' }}>
                                        <p>{job.desc}</p>
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
                    </div>
                )}
            </Popup>
            <Popup trigger={
                <div>
                <button className='save-button job-button'>Post a Job</button>
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
                            style={{borderRadius: 5, border: '1px solid gray', height: 25,outline: 'none'}}
                            />
                            <span onClick={addSkill} style={{ marginLeft: '10px', cursor: 'pointer',outline: 'none'}}>&#43;</span>
                            
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
            </div>
        <br></br>
        <h2 style={{textAlign: 'left', marginLeft: '20%'}}>Jobs you have posted</h2>
            {
                myJobs.map((job,i)=>(
                    <>
                    <div className='job-container' key={job.id}>
                        <h3 className='job-header' onClick={() => toggleMyDescription(i)}>{job.title}</h3>
                        <p className='author'>Posted by {job.author}</p>
                        {showMyJobDesc === i && (
                            <div style={{ marginLeft: '2%' }}>
                                <p>{job.desc}</p>
                                <button className='save-button applicant-button' onClick={()=>getApplicants(job.jobId)}>View Applicants</button>
                                <br></br>
                                {showApplicants === job.jobId &&(
                                    applicants.map((applicant)=>(
                                        <div className='applicants-container' key={applicant.id}>
                                            <img src={applicant.profilePicture?`data:image/jpeg;base64,${applicant.profilePicture}`:avatar}
                                            alt='profile pic' className='profile-picture pfp'></img>
                                            <p style={{alignSelf: 'center'}}>{applicant.name}</p>
                                            <a  href={`/VisitProfile/${applicant.id}`} className='profile_link'>Visit Profile</a> 
                                            <br></br>
                                        </div>
                                    )
                                ))}
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
        <br></br>
        <h2 style={{textAlign: 'left', marginLeft: '20%'}}>Jobs your connections have posted</h2>
            {
                connectionsJobs.map((job,i)=>(
                    <>
                    <div className='job-container' key={job.id}>
                        <h3 className='job-header' onClick={() => toggleConnDescription(i,job.jobId)}>{job.title}</h3>
                        <p className='author'>Posted by {job.author}</p>
                        {showConnJobDesc === i && (
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
            <br></br>
            <h2 style={{textAlign: 'left', marginLeft: '20%'}}>Jobs other users have posted</h2>
            {
                othersJobs.map((job,i)=>(
                    <>
                    <div className='job-container' key={job.id}>
                        <h3 className='job-header' onClick={() => toggleOtherDescription(i,job.jobId)}>{job.title}</h3>
                        <p className='author'>Posted by {job.author}</p>
                        {showOtherJobDesc === i && (
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