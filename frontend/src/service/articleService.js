import axios from "axios";
class ArticleService {
    
    fetchArticles(token) {
        const API_URL = "/HomePage/Articles";
        return axios.get(API_URL,{
            params: {token : token},
            responseType: 'json'
        }).then(response => response.data);
    }


    newArticle(article) {
        const API_URL = "/HomePage/newArticle";
        return axios.post(API_URL,article, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }

}

export default new ArticleService();