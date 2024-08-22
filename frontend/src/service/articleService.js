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

    fetchUserLikes(article_id) {
        const API_URL = `/HomePage/Likes/${article_id}`;
        return axios.get(API_URL,{responseType: 'json'}).then(response => response.data);
    }
    addLike(token, article_id) {
        const API_URL = "/HomePage/AddLike";
        return axios.post(API_URL, null, {
            params: {
                token: token,
                article_id: article_id
            },
            headers: {
                'Content-Type': 'application/json' 
            }
        });
    }
    
}

    
    
    
    


export default new ArticleService();