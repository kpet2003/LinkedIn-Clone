import '../styling/HomePage.css';
import React, { useEffect, useState } from 'react';
import UserService from '../service/userService.js'; 
import image_upload from '../icons/image_upload.png';
import video_upload from '../icons/video_upload.png';
import ArticleService from '../service/articleService.js';
import NavigationBar from './NavigationBar.js';




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
        formData.append('image', article.image);

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
            [id]: files[0]
        }));
    };

    const triggerFileInput = () => {
        document.getElementById('fileInput').click();
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

                        <div className='image_container' onClick={triggerFileInput}>
                            <img src={image_upload} alt='upload image' className='upload'  />
                            <p>Upload image </p>
                            <input type='file' onChange={handleFiles} className='article-image ' id='fileInput'/> 
                        </div>

                        <div className='video_container' onClick={triggerFileInput}>
                            <img src={video_upload} alt='upload video' className='upload'  /> 
                            <p>Upload video </p>
                            <input type='file' onChange={handleFiles} className='article-image ' id='fileInput'/> 
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
        <div>
         <NavigationBar/>
         <NewPost/>
         <Timeline/>
        </div>
      );
}

export default HomePage;
