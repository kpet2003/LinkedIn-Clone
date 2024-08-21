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

    fetchLikes(article_id) {
        const API_URL = `/HomePage/LikesPerArticle/${article_id}`;
        return axios.get(API_URL,{responseType: 'json'}).then(response => response.data);
    }
    
}

export default new ArticleService();