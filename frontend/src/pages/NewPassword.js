import '../Settings.css'

function NewPassword(){
    return(
        <div>
           <div className='password-table'>
            <h1>Change Password</h1><br></br>
            <form>
                <label>New Password: </label>
                <input type='password'></input><br></br><br></br>
                <label>Repeat New Password: </label>
                <input type='password'></input><br></br><br></br>
                <input type='submit' value="Change"></input>
            </form>
        </div> 
        </div>
    );
}

export default NewPassword;