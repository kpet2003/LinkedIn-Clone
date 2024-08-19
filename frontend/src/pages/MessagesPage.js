import React from "react";
import NavigationBar from './HomePage';
import avatar from '../icons/avatar.png'
import '../styling/Messages.css';

function Chat(){
    return(
        <div>
            <NavigationBar></NavigationBar>
            <br></br>
            <div className="chatbox">
                <div className="users-chats">
                    <button className="user-button"><img src={avatar} alt="profile" className="button-image"></img> user 1</button>
                </div>
                <div className="right-side-box">
                    <div className="texts-container">
                        <div className="text-left">
                            <div className="text-bubble-me">
                                <p>This is the text message</p>
                                <span className="time-and-date-me">00:00</span>
                            </div>
                            <img src={avatar} alt="profile" className="chat-pfp-me"></img>
                        </div>
                        <br></br>
                        <div className="text-right">
                            <img src={avatar} alt="profile" className="chat-pfp-other"></img>
                            <div className="text-bubble-other">
                                <p>This is the text message</p>
                                <span className="time-and-date-other">00:00</span>
                            </div>
                        </div>
                    </div>
                    <br></br>
                    <div className="write-bar">
                        <input type="text" id="text" className="enter-text"></input>
                        <input type="Submit" id="send-text" value={'Send'} className="send-button"></input>
                    </div>
                </div> 
            </div>
        </div>
    );
}

export default Chat;