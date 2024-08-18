import axios from "axios";
class ArticleService {
    fetchArticles(token) {
        alert(token);
        const API_URL = "/HomePage/Articles";
        return axios.get(API_URL,{
            params: {token : token},
            responseType: 'json'
        }).then(response => response.data);
    }
}

export default new ArticleService();