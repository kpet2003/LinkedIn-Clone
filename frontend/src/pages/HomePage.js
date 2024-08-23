import '../styling/HomePage.css';
import React, { useEffect, useState } from 'react';
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
                console.log("Fetched data: ",data);
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


function Comments({article_id}) {
    return (
        <div>
            Comments for article ID: {article_id}
        </div>
    );
}


function Timeline() {

    const [articles, setArticles] = useState([]);
    
    // amount of likes per article
    const [likes, setLikes] = useState({});

    // list of users who have liked a specific article
    const [likedUsers, setLikedUsers] = useState({});

    // store whether the user has liked each article
    const [isLiked, setIsLiked] = useState({});

    // store newComment
    const [newComment, setNewComment] = useState({});

    // store whether the comments of the article should be visible
    const [areCommentsVisible,setVisible] =  useState({});

    // save comment count per article
    const [commentCount,setCommentCount] = useState({});

    // fetch the articles in the feed
    useEffect(() => {
        const getArticles = async() => {
            try {
                const token = localStorage.getItem('jwt_token');
                const response = await  ArticleService.fetchArticles(token);
                setArticles(response);
                console.log(response);
            } 
            catch (error) {
                console.error("There was an error getting the request list", error);
            }
        };
        getArticles();
    }, []);

    
    // fetch the amount of likes per article
    const getLikes = async(article_id) => {
        try {
            const likeCount  =  await articleService.fetchLikes(article_id);
            setLikes(prevLikes => ({...prevLikes,[article_id]: likeCount}));
            console.log(`Likes for article ${article_id}: `, likeCount);      
        }
        catch(error) {
            console.log("Error fetching likes",error);
        }
    }
    
    // fetch the users who have liked each article
    const getLikedUsers = async(article_id) => {
        try {
            const response  =  await articleService.fetchUserLikes(article_id);
            setLikedUsers(prevLikes => ({...prevLikes,[article_id]: response}));
            console.log(`Likes for article ${article_id}: `, response);
            

            response.forEach(likedUser => {
                console.log(`Liked User ID: ${likedUser.id}`);
            })
            
            const token = localStorage.getItem('jwt_token');
            
            const user = await userService.getUserData(token);
            console.log("user id: ",user.id)
            const hasUserLiked = response.some(likedUser => likedUser.id === user.id);
            setIsLiked(prevIsLiked => ({ ...prevIsLiked, [article_id]: hasUserLiked }));
            
        }
        catch(error) {
            console.log("Error fetching likes",error);
        }
    }
    
    // fetch the amount of comments per article
    const getCommentCount = async(article_id) => {
        try {
            const commentCount  =  await articleService.fetchCommentCount(article_id);
            setCommentCount(prevComments => ({...prevComments,[article_id]: commentCount}));
            console.log(`Comments for article ${article_id}: `, commentCount);      
        }
        catch(error) {
            console.log("Error fetching comment count",error);
        }
    }

    // load the likes,comments for the articles
    useEffect(() => {
        articles.forEach(article => {
            getLikes(article.id);
            getLikedUsers(article.id);
            getCommentCount(article.id);
        });
        }, [articles]
    );
    
    // link to profile
    const gotoProfile = (user_id) => {
        console.log(user_id);
        const link = document.createElement('a');
        link.href =  `/VisitProfile/${user_id}`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    
    // like an article
    const AddLike = async(article_id) => {
        try {
            const token = localStorage.getItem('jwt_token');
            const response = await articleService.addLike(token,article_id);
            const previous_state = isLiked[article_id];
            setIsLiked((prevIsLiked) => ({ ...prevIsLiked, [article_id]: !previous_state }));
            if(previous_state === false) {
                setLikes((prevLikes) => ({ ...prevLikes, [article_id]: prevLikes[article_id] + 1 }));
            }
            else {
                setLikes((prevLikes) => ({ ...prevLikes, [article_id]: prevLikes[article_id] - 1 }));
            }
            
        }
        catch(error) {
            console.log("There was an error adding the like:",error)
        }
    }


    const toggleVisibility = (article_id) => {
        setVisible(prevShowComments => ({
            ...prevShowComments,
            [article_id]: !prevShowComments[article_id]
        }));
    }

    // handle the text input for new comment
    const handleChange = (event, article_id) => {
        setNewComment(prevComments => ({
            ...prevComments,
            [article_id]: event.target.value
        }));
    };


    // post the new comment
    const postComment = async(article_id) => {

        try {
            const token = localStorage.getItem('jwt_token');
            const response = await articleService.addComment(token,article_id,newComment[article_id]);
            setCommentCount((prevComments) => ({ ...prevComments, [article_id]: prevComments[article_id] + 1 }));

        }
        catch(error) {
            console.error("There was an error posting the comment: ",error);
        }
    }


    return(
        <div>
            <div className='articles_container'>
            {articles.map(article => (
                    <span key={article.id} className='article' >
                        <div className='intro'>
                            <p className='title'>{article.title} by    </p> 
                            <img src={`data:image/jpeg;base64,${article.author.profilePicture}` } alt = 'author'className='author_pfp'/>
                            <p className='author_name'  onClick={() => gotoProfile(article.author.id)}> {article.author.firstName} {article.author.lastName}  </p>
                            
                        </div>

                        <div className='article_content'>
                            <p className='description'>{article.content}</p> 
                        </div>

                        <div className='article_media'>
                           {article.picture &&(<img src={`data:image/jpeg;base64,${article.picture}`} alt='profile' className='article_picture' />) }
                           {article.video &&(<video src={`data:image/jpeg;base64,${article.video}`} alt='profile' className='article_video' controls />) }
                        </div>

                        <div className='likes_display'>
                            <p>{likes[article.id] } likes</p>
                            <p onClick={()=>toggleVisibility(article.id) }className='display_comments'> {commentCount[article.id]} comments</p>
                        </div>

                        <div className='add'>
                            <div >
                                {isLiked[article.id] && (<img src={blue_like} onClick={() => AddLike(article.id)}  alt='blue' className='like_button'/>  ) }
                                {!isLiked[article.id] && (<img src={white_like} onClick={() => AddLike(article.id)}  alt='white' className='like_button'/>  ) }
                            </div>
                            <div className='add_comment'>
                                <textarea className='new_comment' placeholder='Add your comment' onChange={(event) => handleChange(event, article.id)} id='new_comment' rows={1}/>
                                <input type='button' value="Post comment" className='post_button' onClick={()=>postComment(article.id)} />
                            </div>
                        </div>

                        {areCommentsVisible[article.id] && <Comments article_id={article.id} />}
                    </span>
                ))}
            </div>
        </div>
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
