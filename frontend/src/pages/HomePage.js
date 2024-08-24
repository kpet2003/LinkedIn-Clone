import '../styling/HomePage.css';
import React, { useEffect, useState,Suspense } from 'react';
import userService from "../service/userService.js";
import image_upload from '../icons/image_upload.png';
import video_upload from '../icons/video_upload.png';
import ArticleService from '../service/articleService.js';
import NavigationBar from './NavigationBar.js';
import {useNavigate} from "react-router-dom";
import articleService from '../service/articleService.js';
import white_like from '../icons/white_like.jpg';
import blue_like from '../icons/blue_like.jpg';

function NewPost() {

    const token = localStorage.getItem('jwt_token');
    
    const initialState = {
        author_token:token,
        title:'',
        article_content:'',
        image: null,
        video:null
    };


    const [article,setArticle] = useState(initialState);

    const handlePost = async(event) => {

        event.preventDefault();
        const formData = new FormData();
        formData.append('author_token', article.author_token);
        formData.append('title', article.title);
        formData.append('article_content', article.article_content);
        formData.append('image', article.image || null);
        formData.append('video', article.video || null);
        
        
        try {
            const response =  await ArticleService.newArticle(formData);
            console.log(response.data); 
        } 
        
        catch (error) {
            console.error("There was an error creating the article:", error);
        }


    }

    const handleChange = (event) => {
        setArticle({
            ...article,
            [event.target.id]: event.target.value,
        });
    };

    const handleFiles = (event) => {
        const { id, files } = event.target;
        setArticle(prevState => ({
            ...prevState,
            [id === 'input_image' ? 'image' : 'video']: files[0]
        }));
    };

    const triggerFileInput = (input_id) => {
        document.getElementById(input_id).click();
    };
   



    return (
        <div>
            <div className='new_post'>
                <form onSubmit={handlePost}>

                    <div className='text-input'>
                        <textarea required value = {article.title} placeholder='Article Title' className='article_title' onChange={handleChange} id='title' rows={1}/>
                        <textarea required value = {article.article_content} placeholder= "What's on your mind? " onChange={handleChange} id='article_content' className='content' rows={1}/>
                    </div>
                    
                    <div className='article_buttons'>

                        <div className='image_container'onClick={() => triggerFileInput('input_image')}>
                            <img src={image_upload} alt='upload image' className='upload'  />
                            <p>Upload image </p>
                            <input type='file' onChange={handleFiles} className='article-image ' id='input_image'/> 
                        </div>

                        <div className='video_container' onClick={() => triggerFileInput('input_video')}>
                            <img src={video_upload} alt='upload video' className='upload'  /> 
                            <p>Upload video </p>
                            <input type='file' onChange={handleFiles} className='article-image ' id='input_video'/> 
                        </div>

                        <div>
                            <input type='submit' className='submitbutton' value='Post' />
                        </div>
                        
                        
                    </div>

                </form>
            </div>
        </div>
    );  
}

function Profile() {
    
    const navigate = useNavigate();
    const [user, setUser] = useState([]);

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const data = await userService.getUserData(localStorage.getItem('jwt_token'));
                console.log("Fetched user data: ",data);
                setUser(data);
                console.log(user);
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };

        fetchUserData();
    }, []);

    const handleClick = (event) => {
        const {id} = event.target;

        if(id === "visit_profile") {
            navigate("/Profile");
        }
        else {
            navigate("/Network");
        }
    };


    return (
        <div className='user_profile'>
            <img src={`data:image/jpeg;base64,${user.profilePicture}`} alt='profile' className='user_pic' />
            <h3 className='user_name'>{user.firstName} {user.lastName}</h3>
            <input type='button' className='profile_button' id="visit_profile" onClick={handleClick} value="View Profile"/> 
            <input type='button' className='network_button' id="view_network" value="View Network"onClick={handleClick}/>
        </div>
    );

}


function Comments({comments}) {


    // link to profile
    const gotoProfile = (user_id) => {
        console.log(user_id);
        const link = document.createElement('a');
        link.href =  `/VisitProfile/${user_id}`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    return (
        <div className='comments_section'>
            {comments.length ? (
                    comments.map(comment => (
                        <span key={comment.id} className='comment' >
                            <div className='comment_intro'>
                            <img src={`data:image/jpeg;base64,${comment.poster.profilePicture}` } alt = 'author'className='author_pfp'/>
                            <p  className='comment_author' onClick={() => gotoProfile(comment.poster.id)}> {comment.poster.firstName} {comment.poster.lastName}  </p>
                            </div>
                            <div className='comment_content'>
                                <p> {comment.comment}</p>
                            </div>

                        </span>))) : (
                <p>No comments yet</p>)}
            
        </div>
    );
}



function Timeline() {

    const [articleData, setArticleData] = useState([]);
    const [NewComment,setNewComment] = useState([]);
 

    useEffect(() => {
        const getArticles = async () => {
            try {
                const token = localStorage.getItem('jwt_token');
                
                //fetch articles and their data
                const articleDataArray = await ArticleService.fetchArticleData(token);
                setArticleData(articleDataArray);
            } catch (error) {
                console.error("There was an error getting the article list", error);
            }
        };

        getArticles();
    }, []);

    const gotoProfile = (user_id) => {
        console.log(user_id);
        const link = document.createElement('a');
        link.href = `/VisitProfile/${user_id}`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };



     // like an article
     const AddLike = async(article_id,previous_state) => {
        try {
            const token = localStorage.getItem('jwt_token');
            const response = await articleService.addLike(token,article_id);
            
            setArticleData((prevArticleData) =>
                prevArticleData.map((article) => {
                    if (article.article.id === article_id) {
                        return {
                            ...article,
                            isLikedByUser: !previous_state,
                            likes_count: previous_state ? article.likes_count - 1 : article.likes_count + 1,
                        };
                    }
                    return article;
                })
            );
            
        }
        catch(error) {
            console.log("There was an error adding the like:",error)
        }
    }



    const handleChange = (event) => {
        setNewComment(event.target.value);
    };

    const postComment = async(article_id) => {

        try {
            const token = localStorage.getItem('jwt_token');
            const response = await articleService.addComment(token,article_id,NewComment);
            setArticleData((prevArticleData) =>
                prevArticleData.map((article) => {
                    if (article.article.id === article_id) {
                        return {
                            ...article,
                            comments_count: article.comments_count + 1
                        };
                    }
                    setNewComment('');
                    return article;
                })
            );
           
        }
        catch(error) {
            console.error("There was an error posting the comment: ",error);
        }
       
    }

    const toggleVisibility = (article_id,previous_state) => {
        setArticleData((prevArticleData) =>
            prevArticleData.map((article) => {
                if (article.article.id === article_id) {
                    return {
                        ...article,
                        showComments: !previous_state,

                    };
                }
                return article;
            })
        );
    }

    return (
        articleData.map(article=>(
            <span  key = {article.article.id} className='article'>
                {console.log('article data = ',article)}
                <div className='intro'>
                            <p className='title'>{article.article.title} by    </p> 
                            <img src={`data:image/jpeg;base64,${article.article.author.profilePicture}` } alt = 'author'className='author_pfp'/>
                            <p className='author_name'  onClick={() => gotoProfile(article.article.author.id)}> {article.article.author.firstName} {article.article.author.lastName}  </p>
                </div>

                <div className='article_content'>
                    <p className='description'>{article.article.content}</p> 
                </div>

                <div className='article_media'>
                    {article.article.picture &&(<img src={`data:image/jpeg;base64,${article.article.picture}`} alt='profile' className='article_picture' />) }
                    {article.article.video &&(<video src={`data:image/jpeg;base64,${article.article.video}`} alt='profile' className='article_video' controls />) }
                </div>

                <div className='likes_display'>
                            <p>{article.likes_count} likes</p>
                            <p className='display_comments' onClick={()=>toggleVisibility(article.article.id,article.showComments)}> {article.comments_count} comments</p>
                </div>

                <div className='add'>
                    <div >
                        {article.isLikedByUser && (<img src={blue_like} onClick={() => AddLike(article.article.id,article.isLikedByUser)}  alt='blue' className='like_button'/>  ) }
                        {!article.isLikedByUser && (<img src={white_like} onClick={() => AddLike(article.article.id,article.isLikedByUser)}  alt='white' className='like_button'/>  ) }
                    </div>
                    <div className='add_comment'>
                        <textarea className='new_comment' placeholder='Add your comment' onChange={handleChange} id='new_comment' rows={1}/>
                        <input type='button' value="Post comment" className='post_button' onClick={()=>postComment(article.article.id)} />
                    </div>
                </div>
                <Suspense fallback={<div>Loading comments...</div>}>
                    {article.showComments && <Comments comments={article.comments} />}
                </Suspense>
               
            </span>
        )

        )
    );

    
    
}





function HomePage() {
    return (
        <div  >
            <NavigationBar/>
            <div className='homepage'>
                <Profile/>
                <div className='main_content'>
                    <NewPost/>
                    <Timeline/>
                </div>
                
            </div>
            
        </div>
      );
}

export default HomePage;
