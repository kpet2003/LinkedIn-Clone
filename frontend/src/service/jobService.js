import axios from "axios";
const SERVER_URL =  "http://localhost:8080"

class JobService{
    async newJob(data){
        const API_URL = SERVER_URL +"/newJob";
        return (await axios.post(API_URL, data)).data;
    }

    async getJobs(user){
        const url = SERVER_URL + `/jobs/${user}`
        const response = await axios.get(url);
        console.log(response.data);
        return response.data;
    }

    async applyToJob(jobId, token){
        const url = SERVER_URL + `/jobs/${jobId}/${token}`
        const response = await axios.post(url)
    }
}

export default new JobService();