import '../styling/HomePage.css';
import React, { useEffect, useState } from 'react';
import userService from "../service/userService.js";
import image_upload from '../icons/image_upload.png';
import video_upload from '../icons/video_upload.png';
import ArticleService from '../service/articleService.js';
import NavigationBar from './NavigationBar.js';
import {useNavigate,NavLink} from "react-router-dom";



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

function Timeline() {

    const [articles, setArticles] = useState([]);

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


    


    return(
        <div>
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
