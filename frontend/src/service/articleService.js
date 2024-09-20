import axios from "axios";
const SERVER_URL =  "https://localhost:8080"
class ArticleService {
    
    fetchArticleData(token, cancel) {
        const API_URL = SERVER_URL + "/HomePage/ArticleData";
        return axios.get(API_URL,{
            signal: cancel.signal,
            params: {token : token},
            responseType: 'json'
        }).then(response => response.data);
    }

    fetchCategories(cancel) {
        const API_URL = SERVER_URL + "/HomePage/GetCategories";
        return axios.get(API_URL,{
            signal: cancel.signal,
            responseType: 'json'
        }).then(response => response.data);
    }

    newArticle(article) {
        const API_URL = SERVER_URL + "/HomePage/newArticle";
        return axios.post(API_URL,article, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }


    addLike(token, article_id) {
        const API_URL = SERVER_URL + "/HomePage/AddLike";
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

    addComment(token, article_id,comment) {
        const API_URL = SERVER_URL + "/HomePage/AddComment";
        return axios.post(API_URL, null, {
            params: {
                token: token,
                article_id: article_id,
                comment:comment
            },
            headers: {
                'Content-Type': 'application/json' 
            }
        });
    }

    getComments(article_id) {
        const API_URL = SERVER_URL + `/HomePage/GetComments/${article_id}`;
        return axios.get(API_URL,{responseType: 'json'}).then(response => response.data);
    }

    
    
}

    
    
    
    


const articleService =new ArticleService();
export default articleService;