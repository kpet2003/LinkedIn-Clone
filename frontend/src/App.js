import './App.css';

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
    </div>
  );
}

export default App;
