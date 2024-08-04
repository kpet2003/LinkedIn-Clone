import "../Settings.css";

function SettingsPage() {
    return(
        <div className="table">
            <h1><b>Settings</b></h1><br></br>
            <nav>
                <a href="/NewEmail" className="gotolink">Change e-mail</a><br></br><br></br>
                <a href="/NewPassword" className="gotolink">Change password</a>
            </nav>
        </div>
    );
}


export default SettingsPage;