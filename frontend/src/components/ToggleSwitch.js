import React, { useRef, useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import '../styling/ToggleSwitch.css'

//properties of the ToggleSwitch component
ToggleSwitch.propTypes={
    onChange: PropTypes.func,
    value: PropTypes.string,
    name: PropTypes.string,
    checked: PropTypes.bool
}

function ToggleSwitch(props){
    const toggle = useRef(); //to check if toggle has been toggled, initialized with value of last page render
    const checkbox = useRef(); // ToggleSwitch is in reality a checkbox, initialized with value of last page render
    const [isPrivate, setIsPrivate] = useState(false); //to check of user wants this field to be private

    //on page load
    useEffect(() => {
      if (!props.checked) { //if toggle has been clicked on a previous render
          toggle.current.classList.add('toggled'); //save that toggle is toggled
          checkbox.current.checked = true; //save that the checkbox has been clicked
      } else {
          toggle.current.classList.remove('toggled'); //otherwise delete the toggled state
          checkbox.current.checked = false; //save that the checkbox is not clicked
      }
      setIsPrivate(checkbox.current.checked); //if checkbox has been clicked, field is private otherwise it is not
  }, [props.checked]); //property checked needs to be set before page loads

    function handleToggle(event) { //function to handle the user clicking on the toggle
        toggle.current.classList.toggle('toggled'); //save that toggle is toggled
        checkbox.current.checked = !checkbox.current.checked; //if checkbox was saved as clicked, save it as not clicked or the opposite
        setIsPrivate(checkbox.current.checked); //if checkbox has been clicked, field is private otherwise it is not
        if (props.onChange){ //if the onChange function is defined in the properties
            event.target.id = props.id; //set target id of the event (event was the click) to the id pf the properties
            props.onChange(event); //call the onChange function of the properties with the event to handle the new values of the toggle
        }
        
    }
  return (
    <div>
      <label className='label'>{isPrivate ? 'Private' : 'Public'}</label> {/* if the toggle has been set to Private write private on screen else write public */}
      {/* the checkbox that is the toggle */}
      <input
        ref={checkbox} //get the state of the checkbox from above
        name={props.name} //name comes from the properties
        className='toggle-checkbox' //css styling, makes checkbox not show up on screen
        type='checkbox' //define that input is a checkbox
        value={props.value || false} //value is whatever is set in properties or false if properties are not defined yet
      />
      {/* the toggle that the user will interact with */}
      <span
        ref={toggle} //the toggle state from above
        onClick={handleToggle} //handleToggle function is triggered when clicked
        className={props.checked ? 'toggled toggle-switch' : 'toggle-switch'} //what the toggle looks like depends on if it's toggled or not
      >
        <span className='toggle'></span> {/* the little "ball" inside the toggle */}
      </span>
    </div>
  );
}


  export default ToggleSwitch;
