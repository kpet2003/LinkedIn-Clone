import axios from "axios";
const SERVER_URL =  "https://localhost:8080"
class ArticleService {
    
    // fetch all article data for user
    fetchArticleData(token, cancel) {
        const API_URL = SERVER_URL + "/HomePage/ArticleData";
        return axios.get(API_URL,{
            signal: cancel.signal,
            params: {token : token},
            responseType: 'json'
        }).then(response => response.data);
    }

    // fetch all existing article categories
    fetchCategories(cancel) {
        const API_URL = SERVER_URL + "/HomePage/GetCategories";
        return axios.get(API_URL,{
            signal: cancel.signal,
            responseType: 'json'
        }).then(response => response.data);
    }

    // post new article
    newArticle(article) {
        const API_URL = SERVER_URL + "/HomePage/newArticle";
        return axios.post(API_URL,article, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    // add a like to the article
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

    // add a comment to the article
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

    // get comments of article
    getComments(article_id) {
        const API_URL = SERVER_URL + `/HomePage/GetComments/${article_id}`;
        return axios.get(API_URL,{responseType: 'json'}).then(response => response.data);
    }

    
    
}

    
    
    
    


const articleService =new ArticleService();
export default articleService;