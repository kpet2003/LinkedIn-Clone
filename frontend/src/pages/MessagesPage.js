import React from "react";
import NavigationBar from './NavigationBar.js';
import avatar from '../icons/avatar.png'
import '../styling/Messages.css';
import {Client} from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { useState,useEffect,useRef,useCallback } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import userService from "../service/userService";

function Chat(){
    const [privateChats, setPrivateChats] = useState(new Map()); //map of user's chats with other users
    const [tab, setTab] = useState(""); //to know which chat the user is viewing/last viewed
    const [receiverImage, setReceiverImage] = useState(""); //profile picture of the user the current user is messaging
    //initial state of the user's data
    const [userData, setUserData] = useState({
        id: '',
        receivername: '',
        connected: false,
        message: '',
        first_name: '',
        last_name: '',
        email: '',
        image: '',
        lastChatUserId: '',
        connections: []
    });

    const stompClientRef = useRef(null); //initialize reference to stomp client with null or whatever it was in the last render of the page
    const navigate = useNavigate(); //used to navigate to a new page
    const location = useLocation(); //to access useNavigate state, to know if the user is beginning a new chat


    const onConnected = useCallback(() => {
        if (stompClientRef.current) { //if the stomp client is active, subscribe to user's private chat channel
            stompClientRef.current.subscribe(`/user/${userData.id}/chat`, onPrivateMessage, {
                Authorization: `Bearer ${localStorage.getItem('jwt_token')}`
            });
            console.log("WebSocket connected"); //show connected message in console
        }
    }, [userData.id]); //needs user's id to work

    const onError = useCallback((err) => { //if connection error occurs
        console.error("Connection error:", err);
        if (err.headers && err.headers['error'] === 'InvalidToken') {
            localStorage.removeItem('jwt_token'); //remove invalid token
            navigate('/'); //redirect to login page
        }
    },[navigate]);

    const connect = useCallback(() => {
        const token = localStorage.getItem('jwt_token');

        if (!token || stompClientRef.current?.connected) { //if no token or already connected, don't subscribe again
            return;
        }

        // Create WebSocket connection
        const Sock = new SockJS('https://localhost:8080/ws'); //new web socket
        //initialize the stomp client
        stompClientRef.current = new Client({
            webSocketFactory: () => Sock,
            reconnectDelay: 5000,
            connectHeaders: {
                Authorization: `Bearer ${localStorage.getItem('jwt_token')}`
            },
            onConnect: onConnected,
            onStompError: onError
        });

        stompClientRef.current.activate(); //activate the connection to the socket
    },[onConnected, onError]); //needs the onConnected function to work

    //function to get chat history between two users
    const fetchChatHistory = useCallback(async (id) => {
        try {
            if(userData.id){ //if current user's id has been set
                setReceiverImage(""); //initialize the other user's profile picture as an empty string
                setTab(id); //set current chat being viewed with the other user's id
                await userService.setTab(id,localStorage.getItem('jwt_token')); //save in database that the last chat current user viewed is this one
                const messages = await userService.getChatHistory(userData.id, id); //get the users' chat from the database
                setPrivateChats(new Map([[id, messages]])); // map the messages based on id
                const image = await userService.getImage(id); //get the other user's profile picture
                if (image && image !== "undefined") { //if image was retrieved save it in the constant
                    setReceiverImage(`data:image/jpeg;base64,${image}`);
                } else { //otherwise print an error in console
                    console.error("Invalid image data", image);
                }
            }
        } catch (error) { //if an error occurs
            console.error('Error fetching chat history:', error); //print it in console
        }
    }, [userData.id, setReceiverImage, setTab, setPrivateChats]); //these are needed for function to run

    //on page load
    useEffect(() => {
        const cancel = new AbortController(); //useEffect runs multiple times due to react strict mode, use abort controller
                                              //to make request only once
        //function to get user's data
        const fetchUserData = async () => {
            try {
                const user = await userService.getUserChatData(localStorage.getItem('jwt_token'), cancel); //get data from databse
                
                const newUserId = location.state?.userId; //if new chat needs to be made, keep the id of the user the new chat will be with
    
                let updatedConnections = user.connections; //initialize new list as the user's connections
                if (newUserId && updatedConnections.length > 0) {
                    //find the user that the new chat will be with and set the hasMessaged attribute to true so that this user shows up as an option to messsage
                    updatedConnections = updatedConnections.map(conn => {
                        if (conn.id === Number(newUserId) && !conn.hasMessaged) {
                            return { ...conn, hasMessaged: true };
                        }
                        return conn;
                    });
                }
    
                //save the updated list to the user constant along with rest of the data got from backend
                setUserData({
                    id: user.id,
                    first_name: user.first_name,
                    last_name: user.last_name,
                    email: user.email,
                    image: user.image,
                    lastChatUserId: user.lastChatUserId,
                    connections: updatedConnections
                });

                fetchChatHistory(user.lastChatUserId); //get the chat history for the user the current user last texted
                connect(); //connect to a web socket after user data is set
            } catch (error) { //if error occurs
                console.error('Error fetching user data:', error); //write it to console
            }
        };
    
        fetchUserData(); //call funcrion to get user data
    
        return () => { //when returning use abort controller so that request does not happen again
            cancel.abort();
            if (stompClientRef.current) { //also deactivate any connections that were not supposed to happen
                stompClientRef.current.deactivate();
            }
        };
    }, [navigate, connect, location.state, fetchChatHistory]); //needed for function to run


    //function called when message is received from the other user
    const onPrivateMessage = (payload) => {
        const payloadData = JSON.parse(payload.body); // get message data
        setPrivateChats(prev => {
            const newChats = new Map(prev); //get the chats with other users
            if (!newChats.has(payloadData.senderId)) { //if chat with this user does not exist
                newChats.set(payloadData.senderId, []); //create a list for the message with this user
            }

            const existingMessages = newChats.get(payloadData.senderId); //get the existing chat with this user from all chats

            const isDuplicate = existingMessages.some(msg => msg.date === payloadData.date); //check if this messages already exists
            if (!isDuplicate) { //if it is sent for the first time
                existingMessages.push(payloadData); //add it to the messages
                newChats.set(payloadData.senderId, existingMessages); //save the chat in the rest of the chats
            } else {
                console.log("Duplicate message detected, not adding to chat."); //message if duplicate was found
            }
            return newChats;
        });
    };

    //function to save the massage being sent in the user's data
    const handleMessage = (event) => {
        const { value } = event.target;
        setUserData(prevState => ({ ...prevState, message: value }));
    };

    //function to send a message
    const sendPrivateValue = () => {
        if (userData.message === ' ' || userData.message === 'undefined' || userData.message === '') return; //if message is empty or undefined don't send
        if (stompClientRef.current && tab) { //if connected to socket and it's been set which user the current user is messaging
            const tempId = Date.now(); //temporary message id
            //create a message
            const chatMessage = {
                senderId: userData.id,
                receiverId: tab,
                message: userData.message,
                status: "MESSAGE",
                date: new Date().toISOString(),
                tempId
            };
            
            //update the chat the same way as when a message is received
            setPrivateChats(prev => {
                const newChats = new Map(prev); //get the chats with other users
    
                if (!newChats.has(tab)) { //if chat with this user does not exist
                    newChats.set(tab, []); //create a list for the message with this user
                }

                const existingMessages = newChats.get(tab); //get messages with this user

                const isDuplicate = existingMessages.some(msg => msg.tempId === chatMessage.tempId); //check if this message has laready been sent
                if (!isDuplicate) { //if it hasn't, add it to the chat
                    existingMessages.push(chatMessage);
                    newChats.set(tab, existingMessages);
                }
    
                return newChats;
            });
    
            //send the message through the socket
            stompClientRef.current.publish({
                destination: "/app/chat",
                body: JSON.stringify(chatMessage)
            });
    
            //after sending clear the input field
            setUserData(prevState => ({ ...prevState, message: "" }));
        }
    };

    //function to group the messages by the day they were sent
    const groupMessagesByDate = (messages) => {
        return messages.reduce((groups, message) => { //use reduce to go through the messages and accumulate results
            const date = new Date(message.date).toDateString(); //get the day the message was sent
            
            if (!groups[date]) { //if we don't have a list of messages for this day
                groups[date] = []; //initialize it
            }
            
            groups[date].push(message); //add the message to the list of messages for this day
            
            return groups; //return the lists of texts
        }, {}); //list of lists of texts starts as null
    };

    const base64Image = userData.image? `data:image/jpeg;base64,${userData.image}`: `${avatar}`; //if user has a profile picture use it for display, otherwise use the default
    const messagesByDate = tab ? groupMessagesByDate(privateChats.get(tab) || []) : {}; //if tab is valid group messages with this user by date

    return(
        <div>
            <NavigationBar></NavigationBar>
            <br></br>
            <div className="chatbox"> {/* the box the messages and list of users are */}
                <div className="users-chats"> {/* the list of users */}
                <ul className="unordered-list">
                        {/* for every user in user's connections, if the user has messaged them, show a button that will show the chat between them when clicked*/}
                        {userData.connections
                            .filter(connection => connection.hasMessaged)
                            .map((connection) => (
                                <li key={connection.id} className="button-list">
                                    <button //button shows the other user;s profile picture and their name, if this chat has been selected it has a different color
                                        className={`user-button ${tab === connection.id ? 'active' : ''}`}
                                        onClick={() => fetchChatHistory(connection.id)}>
                                        <img
                                            src={connection.image ? `data:image/jpeg;base64,${connection.image}` : `${avatar}`}
                                            alt="profile"
                                            className="button-image profile-picture"
                                        />
                                        {connection.first_name} {connection.last_name}
                                    </button>
                                </li>
                            ))}
                    </ul>
                    
                </div>
                {/* the messages and input to send new message */}
                <div className="right-side-box">
                <div className="texts-container">
                    {/* for every group of messages */}
                    {Object.entries(messagesByDate).map(([date, messages], index) => (
                        <div key={index}>
                            <div className="date-separator">
                                <span>{new Date(date).toLocaleDateString()}</span> {/* show the date this group of messages was sent */}
                            </div>
                            {/* show the messages in chronological order */}
                            {/* depending on who sent it, a message shows up on the right or left side og the box with the user's picture next to it */}
                            {messages.map((chatMessage, i) => (
                                <div key={i} className={chatMessage.senderId === userData.id ? "text-left" : "text-right"}>
                                    {chatMessage.senderId !== userData.id && (
                                        <img
                                            src={receiverImage ? `${receiverImage}` : avatar}
                                            alt="profile"
                                            className="chat-pfp-other profile-picture"
                                        />
                                    )}
                                    <div className={chatMessage.senderId === userData.id ? "text-bubble-me" : "text-bubble-other"}>
                                        <p>{chatMessage.message}</p>
                                        {/*on the bottom of each message the time it was sent shows */}
                                        <span className={chatMessage.senderId === userData.id ? "time-and-date-me" : "time-and-date-other"}>
                                            {new Date(chatMessage.date).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                                        </span>
                                    </div>
                                    {chatMessage.senderId === userData.id && (
                                        <img
                                            src={base64Image || avatar}
                                            alt="profile"
                                            className="chat-pfp-me profile-picture"
                                        />
                                    )}
                                </div>
                            ))}
                        </div>
                    ))}
                    {!tab && <p>You haven't texted anyone yet!</p>} {/* if no chat is available show a message */}
                </div>
                    <br></br>
                    {/* input for a new message and send button */}
                    <div className="write-bar">
                        <textarea id="text" className="enter-text" value={userData.message} onChange={handleMessage}></textarea>
                        <input type="Submit" id="send-text" value={'Send'} className="send-button" onClick={sendPrivateValue}></input>
                    </div>
                </div> 
            </div>
        </div>
    );
}

export default Chat;