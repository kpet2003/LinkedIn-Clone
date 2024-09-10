import axios from "axios";
const SERVER_URL =  "http://localhost:8080"

class JobService{
    async newJob(data){
        const API_URL = SERVER_URL +"/newJob";
        return (await axios.post(API_URL, data)).data;
    }

    async getConnectionJobs(user){
        const url = SERVER_URL + `/connectionjobs/${user}`
        const response = await axios.get(url);
        console.log( 'CONNECTIONS',response.data);
        return response.data;
    }

    async getOtherJobs(user){
        const url = SERVER_URL + `/otherjobs/${user}`
        const response = await axios.get(url);
        console.log('OTHERS', response.data);
        return response.data;
    }

    async getMyJobs(user){
        const url = SERVER_URL + `/myjobs/${user}`
        const response = await axios.get(url);
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
}

export default new JobService();