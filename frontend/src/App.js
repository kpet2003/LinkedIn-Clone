import './App.css';


function ClickButton() {
  return (
    <form>
      <label>E-mail:</label><br></br>
      <input type='text'></input><br></br>
      <label>Username:</label><br></br>
      <input type='text'></input><br></br>
      <input type='submit' value={'Login'}></input>
    </form>
   );
}

function Button(){
  return (
    <div className='Buttons'>
      <button className='WelcomeButton'>
        Sign Up
      </button>
      <button className='WelcomeButton'>
        Login
      </button>
    </div>
  );
}


function App() {
  return (
    <div className="App">
      <h1 className='Buttons'>Welcome</h1>
      <Button></Button>
      <ClickButton></ClickButton>
    </div>
  );
}

export default App;
