import '../SignUp.css';
import {useNavigate} from "react-router-dom";

function SignUpPage() {
    return (
        <div>
            <from>
                <label>E-mail:</label><br></br>
                <input type='email'></input> <br></br>
                <label>First Name:</label><br></br>
                <input type='text'></input><br></br>
                <label>Last Name:</label><br></br>
                <input type='text'></input><br></br>
                <label>Password:</label><br></br>
                <input type='password'></input><br></br>
                <label>Repeat Password:</label><br></br>
                <input type='password'></input><br></br>
                <label>Phone Number:</label><br></br>
                <input type='tel'></input><br></br>
                <label>Profile Picture:</label><br></br>
                <input type='file'></input><br></br>
                <label>CV:</label><br></br>
                <input type='file'></input><br></br>
                <label>Reset:</label><br></br>
                <input type='reset' value="Reset"></input><br></br>

            </from>
        </div>
      );
}

export default SignUpPage;