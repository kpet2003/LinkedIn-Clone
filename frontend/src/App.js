import './App.css';


function ClickButton() {
  return (
    <div className='login'>
       <h1 >Welcome</h1>
      <form>
        <label>E-mail:</label><br></br>
        <input type='text'></input><br></br>
        <label>Password:</label><br></br>
        <input type='text'></input><br></br>
        <input className='LoginButton' type='submit' value={'Login'}></input><br></br>
        <div className='signup'>
          Create an account:
          <input className='WelcomeButton' type='submit' value={'Sign Up'}></input>
        </div>
      </form>
    </div>
   );
}

function WelcomePage(){
  return (
    <div>
     <ClickButton />
    </div>
  );
}


function App() {
  return (
    <div className="App">
       <WelcomePage />
    </div>
  );
}

export default App;
