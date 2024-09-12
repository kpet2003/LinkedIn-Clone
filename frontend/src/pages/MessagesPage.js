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
    const [privateChats, setPrivateChats] = useState(new Map());
    const [tab, setTab] = useState("");
    const [receiverImage, setReceiverImage] = useState("");
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

    const stompClientRef = useRef(null); // UseRef to hold stompClient
    const navigate = useNavigate(); // Hook to navigate programmatically
    const location = useLocation(); //to access useNavigate state


    const onConnected = useCallback(() => {
        if (stompClientRef.current) {
            stompClientRef.current.subscribe(`/user/${userData.id}/chat`, onPrivateMessage, {
                Authorization: `Bearer ${localStorage.getItem('jwt_token')}`
            });
            console.log("WebSocket connected");
        }
    }, [userData.id]);

    const connect = useCallback(() => {
        const token = localStorage.getItem('jwt_token'); // Retrieve the JWT token from localStorage

        if (!token || stompClientRef.current?.connected) {
            // If no token or already connected, prevent re-subscription
            return;
        }

        // Create WebSocket connection
        const Sock = new SockJS('https://localhost:8080/ws');
        stompClientRef.current = new Client({
            webSocketFactory: () => Sock,
            reconnectDelay: 5000,
            connectHeaders: {
                Authorization: `Bearer ${localStorage.getItem('jwt_token')}` // Include the token in the headers
            },
            onConnect: onConnected,
            onStompError: onError
        });

        stompClientRef.current.activate();
    },[onConnected]);

    useEffect(() => {
        // Fetch user data when the component mounts
        const fetchUserData = async () => {
            try {
                const user = await userService.getUserChatData(localStorage.getItem('jwt_token'));
                
                // Check if newUserId exists in location.state and if connections need to be updated
                const newUserId = location.state?.userId;
    
                // Check if connections are already populated and if newUserId exists
                let updatedConnections = user.connections;
                console.log(newUserId)
                console.log(updatedConnections.length)
                if (newUserId && updatedConnections.length > 0) {
                    // Find the new user in connections and update hasMessaged to true
                    console.log('im in')
                    updatedConnections = updatedConnections.map(conn => {
                        console.log(`Checking connection with id: ${conn.id} and hasMessaged: ${conn.hasMessaged}`);
                        console.log(newUserId)
                        if (conn.id === Number(newUserId) && !conn.hasMessaged) {
                            console.log("FOUND!")
                            return { ...conn, hasMessaged: true };
                        }
                        return conn;
                    });
                    console.log(updatedConnections)
                }
    
                // Set the user data, including updated connections
                setUserData({
                    id: user.id,
                    first_name: user.first_name,
                    last_name: user.last_name,
                    email: user.email,
                    image: user.image,
                    lastChatUserId: user.lastChatUserId,
                    connections: updatedConnections // Set updated connections with hasMessaged changes
                });
                fetchChatHistory(user.lastChatUserId);
                console.log('SET TAB',tab)
                connect(); // Call connect function after user data is set
                console.log(user);
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };
    
        fetchUserData();
    
        return () => {
            if (stompClientRef.current) {
                stompClientRef.current.deactivate();
            }
        };
    }, [navigate, connect, location.state]);

    
    const onPrivateMessage = (payload) => {
        const payloadData = JSON.parse(payload.body);
        console.log("Received message: ", payloadData);
        setPrivateChats(prev => {
            const newChats = new Map(prev);
            if (!newChats.has(payloadData.senderId)) {
                newChats.set(payloadData.senderId, []);
            }
            newChats.get(payloadData.senderId).push(payloadData);
            return newChats;
        });
    };

    const onError = (err) => {
        console.error("Connection error:", err);
        if (err.headers && err.headers['error'] === 'InvalidToken') {
            localStorage.removeItem('jwt_token'); // Remove invalid token
            // navigate('/'); // Redirect to login page
        }
    };

    const handleMessage = (event) => {
        const { value } = event.target;
        setUserData(prevState => ({ ...prevState, message: value }));
    };

    const sendPrivateValue = () => {
        console.log('SENDING TO ',tab);
        if (userData.message === ' ' || userData.message === 'undefined') return;
        if (stompClientRef.current && tab) {
            const chatMessage = {
                senderId: userData.id,
                receiverId: tab,
                message: userData.message,
                status: "MESSAGE"
            };
    
            setPrivateChats(prev => {
                const newChats = new Map(prev);
    
                // Check if the tab (receiver's ID) exists in the map
                if (!newChats.has(tab)) {
                    newChats.set(tab, []); // Initialize an empty array if it doesn't exist
                }
    
                // Now we are sure that newChats.get(tab) will return an array
                newChats.get(tab).push(chatMessage);
    
                return newChats;
            });
    
            // Send the message through the WebSocket
            stompClientRef.current.publish({
                destination: "/app/chat",
                body: JSON.stringify(chatMessage)
            });
    
            // Clear the message input after sending
            setUserData(prevState => ({ ...prevState, message: "" }));
        }
    };


    const fetchChatHistory = async (id) => {
        try {
            setReceiverImage("")
            setTab(id);
            await userService.setTab(id,localStorage.getItem('jwt_token'));
            const messages = await userService.getChatHistory(userData.id, id);
            setPrivateChats(new Map([[id, messages]]));
            const image = await userService.getImage(id);
            if (image && image !== "undefined") {
                console.log('got image');
                setReceiverImage(`data:image/jpeg;base64,${image}`);
            } else {
                console.error("Invalid image data", image);
            }
            
        } catch (error) {
            console.error('Error fetching chat history:', error);
        }
    };

    const groupMessagesByDate = (messages) => {
        return messages.reduce((acc, message) => {
            const dateKey = new Date(message.date).toDateString(); // Group by day
            if (!acc[dateKey]) {
                acc[dateKey] = [];
            }
            acc[dateKey].push(message);
            return acc;
        }, {});
    };
    

    const base64Image = userData.image? `data:image/jpeg;base64,${userData.image}`: `${avatar}`;
    const messagesByDate = tab ? groupMessagesByDate(privateChats.get(tab) || []) : {};
    console.log(tab)

    return(
        <div>
            <NavigationBar></NavigationBar>
            <br></br>
            <div className="chatbox">
                <div className="users-chats">
                <ul className="unordered-list">
                        {userData.connections
                            .filter(connection => connection.hasMessaged)
                            .map((connection) => (
                                <li key={connection.id} className="button-list">
                                    <button
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
                <div className="right-side-box">
                <div className="texts-container">
                    {Object.entries(messagesByDate).map(([date, messages], index) => (
                        <div key={index}>
                            <div className="date-separator">
                                <span>{new Date(date).toLocaleDateString()}</span>
                            </div>
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
                    {!tab && <p>Select a chat</p>}
                </div>

                    <br></br>
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