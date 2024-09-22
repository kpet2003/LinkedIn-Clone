import axios from "axios";
const SERVER_URL =  "https://localhost:8080"
// functions relating to job data that handle the requests to the backend 
class JobService{
    async newJob(data){
        const API_URL = SERVER_URL +"/newJob";
        return (await axios.post(API_URL, data)).data;
    }

    async getConnectionJobs(user, cancel){
        const url = SERVER_URL + `/connectionjobs/${user}`
        const response = await axios.get(url,{
            signal: cancel.signal
        });
        console.log( 'CONNECTIONS',response.data);
        return response.data;
    }

    async getOtherJobs(user, cancel){
        const url = SERVER_URL + `/otherjobs/${user}`
        const response = await axios.get(url,{
            signal: cancel.signal
        });
        console.log('OTHERS', response.data);
        return response.data;
    }

    async getMyJobs(user, cancel){
        const url = SERVER_URL + `/myjobs/${user}`
        const response = await axios.get(url,{
            signal: cancel.signal
        });
        console.log('THIS USER', response.data);
        return response.data;
    }

    async applyToJob(jobId, token){
        const url = SERVER_URL + `/jobs/${jobId}/${token}`
        const response = await axios.post(url)
    }

    async getJobsApplied(token){
        const url = SERVER_URL +`/applied/${token}`;
        const response = await axios.get(url);
        return response.data;
    }

    async getJobApplicants(jobId, token){
        const url = SERVER_URL + `/applicants/${jobId}/${token}`
        const response = await axios.get(url)
        return response.data
    }

    async addView(jobId, token) {
        const url = SERVER_URL + `/addView/${jobId}/${token}`
        const response = await axios.put(url);
        return response.data;
    }


}

export default new JobService();