
import '../styling/HomePage.css';
import '../styling/Jobs.css'
import React, { useEffect, useState } from 'react';
import jobService from "../service/jobService.js";
import avatar from '../icons/avatar.png';
import NavigationBar from './NavigationBar.js';
import Popup from 'reactjs-popup';



function Jobs() {

    //initial state of a new job to be posted
    const initialState = {
        title: '',
        desc: '',
        skills: []
    };

    const [myJobs, setMyJobs] = useState([]); //list to keep jobs user has posted
    const [connectionsJobs, setConnectionsJobs] = useState([]); //list to keep jobs the user's connections have posted
    const [othersJobs, setOthersJobs] = useState([]); //list to keep jobs posted by users not connected to the user
    const [newJob, setNewJob] = useState(initialState); //new job to be posted by the user
    const [newSkill, setNewSkill] = useState(''); //to keep skills of a job
    const [showMyJobDesc, setShowMyJobDesc] = useState(null); //to show job description of a job in the myJobs list
    const [showConnJobDesc, setShowConnJobDesc] = useState(null); //to show job description of a job in the connectionsJobs list
    const [showOtherJobDesc, setShowOtherJobDesc] = useState(null); //to show job description of a job in the othersJobs list
    const [showAppliedJobDesc, setShowAppliedJobDesc] = useState(null); //to show job description of a job the user has applied to
    const [jobsApplied, setJobsApplied] = useState([]); //list of jobs the user has applied to
    const [showApplicants, setshowApplicants] = useState(null); //to show the users that have applied to a job posted by the user
    const [applicants, setApplicants] = useState([]); //list of users that have applied to jobs the user has posted

    //on page load
    useEffect(() => {
        //use effect runs multiple times due to react strict mode, use abort controllers to stop requests from happening more than once
        const cancelJobs = new AbortController();
        const cancelConnections = new AbortController();
        const cancelOthers = new AbortController();
        //functions to get user's data
        const fetchData = async () => {
            try {
                const [myJobs, connectionsJobs, othersJobs] = await Promise.all([ //wait for these requests:
                    jobService.getMyJobs(localStorage.getItem('jwt_token'), cancelJobs), //get jobs user has posted
                    jobService.getConnectionJobs(localStorage.getItem('jwt_token'), cancelConnections), //get jobs user's connections have posted
                    jobService.getOtherJobs(localStorage.getItem('jwt_token'), cancelOthers) //get jobs other users have posted
                ]);
                //save the data got in the corresponding list
                setMyJobs(myJobs);
                setConnectionsJobs(connectionsJobs);
                setOthersJobs(othersJobs);
            } 
            catch (error) { //if an error occurs (can be a cancelled request), show error message
                console.error("There was an error getting the available jobs", error);
            }
        }
        fetchData(); //call the data to get user's data
        //once requests have been made once, make sure they arent't made again by calling the abort controllers when function returns
        return()=>{
            cancelJobs.abort();
            cancelConnections.abort();
            cancelOthers.abort();
        }
    },[]);

    //function to handle the change of a field in the new job
    const handleChange = (event) => {
        setNewJob({
            ...newJob, //leave the other fileds unchanged
            [event.target.id]: event.target.value, //change the value of the changed field to the neew value
        });
    };

    //function to handle a new skill being added to the new job
    const handleSkillInput = (event) => {
        setNewSkill(event.target.value); //set the skill as user's input
    };

    //function to add the new skill to the skills already added to the new job
    const addSkill = () => {
        if (newSkill.trim() !== '') {  //make sure skill input is not empty
          setNewJob((prevState) => ({
            ...prevState, //leave the rest of new job as it was
            skills: [...prevState.skills, newSkill] //add the new skill to the list of skills
          }));
          setNewSkill('');  //clear the input field after adding
        }
    };

    //function to handle submission of a new job
    const handleSubmit = async(event) => {
        event.preventDefault(); //prevent the default get request from happening

        //set the data of the new job using the data of the newJob constant
        const data={
            authorToken: localStorage.getItem('jwt_token'),
            jobTitle: newJob.title,
            jobDesc: newJob.desc,
            jobSkills: newJob.skills
        };
        try {
            await jobService.newJob(data); //save the new job to the database
            alert('Job submitted successfully'); //message for the user
            reset(); //reset the input of new job so that old values don't show
        } catch (error) { //if an error occurs
            console.log('Error posting: ', error); //show error in console
            alert('There was an error posting the job'); //message for user
        }
    }

    //reset function used to reset the new job input fields
    const reset = ()=>{
        setNewJob(initialState); //re-initialize the constant
        setNewSkill(''); //clear the input of new skills
    }

    //next 3 functions keep which job description a user is reading on each of the 3 job lists

    const toggleMyDescription = (i) => { //i is the id of the job the user is reading
        if (showMyJobDesc === i) { //if user was reading this job and has clicked the show decription button a second time
            setShowMyJobDesc(null); //hide the description
        } else { //otherwise show the description of this job
            setShowMyJobDesc(i); 
        }
    };

    const toggleConnDescription = (i,job_id) => { //gets the id of the job in both the frontend and backend lists
        if (showConnJobDesc === i) { //if user was reading this job and has clicked the show decription button a second time
            setShowConnJobDesc(null); //hide the description
        } else {
            setShowConnJobDesc(i); //otherwise show the description of this job
            try {
                jobService.addView(job_id,localStorage.getItem('jwt_token')); //add a view to this job
            }
            catch(error) { //if error occurs write it to console
                console.log('error adding view to job: ',error);
            }
        }
    };

    //works the same way as the toggleConnDescription function but for the othersJobs list instead of the connectionsJobs
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

    //works the same as the toggleMyDescription function but for the applied jobs
    const toggleAppliedDescription = (i) => {
        if (showAppliedJobDesc === i) {
            setShowAppliedJobDesc(null);
        } else {
            setShowAppliedJobDesc(i);
        }
    };

    //function to handle the user applying to a job
    const apply = async(i)=>{ //i is the id of the job
        try {
            await jobService.applyToJob(i, localStorage.getItem('jwt_token')); //save the application in database
            alert("Applied successfully"); //message for user
            
        } catch (error) { //if error occurs
            console.error("Error applying for job:", error); //write it to console
            alert("There was an error applying for the job"); //message for user
        }
    }

    //function to get the jobs the user has applied to
    const getJobsApplied = async()=>{
        try{
        const response = await jobService.getJobsApplied(localStorage.getItem('jwt_token')); //make backend request
        setJobsApplied(response); //save the jobs
        }catch(error){ //if error occurs
            console.error("Error getting jobs applied:",error); //write it to console
            alert("Could not get the jobs you have applied for"); //message for user
        }
    }

    //function to get the users that have applied to a job the user posted, works similar to the toggle description funtions
    const getApplicants = async(i)=>{
        try {
            if (showApplicants === i) { //if the applicants of this job were showing
                setshowApplicants(null); //hide them
            } else {
                const data = await jobService.getJobApplicants(i, localStorage.getItem('jwt_token')); //get the applicants from database
                setApplicants(data); //save them
                setshowApplicants(i); //show applicants of this job
            }
            
        } catch (error) { //if error occurs
            console.error("Error getting applicants:", error); //write it to console
            alert("There was an error getting the applicants"); //message for user
        }
    }

    return (
        <div>
            <div className='buttons-container'> {/* buttons at the top of the screen */}
            {/* popup that will show the list of jobs that the user has applied to */}
            <Popup trigger={
                <div> {/* first button shows jobs the user has applied to when clicked */}
                <button className='save-button job-button' onClick={getJobsApplied}>Jobs Applied For</button>
                </div>
            } modal closeOnDocumentClick className="modal-content">
                {(close) => ( //popup will close when the x icon is clicked (x icon is the span below)
                    <div className='modal-background'>
                        <span className="close" onClick={close}>
                        &times;
                        </span><br></br><br></br>
                        <div className='view-jobs'>
                    {
                        jobsApplied.map((job,i)=>( //show the jobs that have been applied
                            <>
                            <div className='in-view' key={job.id}> {/* the box the job is in */}
                                <h3 className='job-header' onClick={() => toggleAppliedDescription(i)}>{job.title}</h3> {/* title of job, when clicked show description */}
                                <p className='author'>Posted by {job.author}</p> {/* show the user that posted it */}
                                {showAppliedJobDesc === i && ( //if this job's title has been clicked show the description
                                    <div style={{ marginLeft: '2%' }}>
                                        <p>{job.desc}</p>
                                    </div>
                                )}
                                <span className='footer'> {/* footer of the box is the time and date the job was posted */}
                                    {new Date(job.date).toLocaleDateString()} {/* date */}
                                    &nbsp; {/* space */}
                                    {new Date(job.date).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })} {/* time */}
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
            
            {/* popup that will show the form to be filled in to post a new job */}
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
                            onChange={handleSkillInput}
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
        {/* show list of jobs the user posted */}
        <h2 style={{textAlign: 'left', marginLeft: '20%'}}>Jobs you have posted</h2>
            {
                myJobs.map((job,i)=>( //jobs are displayed in the same way as in the jobs applied popup with an added button to show applicants
                    <>
                    <div className='job-container' key={job.id}>
                        <h3 className='job-header' onClick={() => toggleMyDescription(i)}>{job.title}</h3>
                        <p className='author'>Posted by {job.author}</p>
                        {showMyJobDesc === i && (
                            <div style={{ marginLeft: '2%' }}>
                                <p>{job.desc}</p>
                                <button className='save-button applicant-button' onClick={()=>getApplicants(job.jobId)}>View Applicants</button>
                                <br></br>
                                {showApplicants === job.jobId &&( //applicants are shown only when the desription is showing and the button is clicked
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
        {/* show list of jobs user's connections have posted, same as above but instead of a show applicants button, we have an apply button */}
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
            {/* show list of jobs the users not connected to the user have posted, same display as user's connections' jobs */}
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
            <NavigationBar></NavigationBar> {/* page has the navigation bar on top and everything else below */}
            <br></br>
            <Jobs></Jobs>
        </div>
     
    );
}

export default JobPage;