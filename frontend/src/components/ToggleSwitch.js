import React, { useRef, useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import '../ToggleSwitch.css'

ToggleSwitch.propTypes={
    onChange: PropTypes.func,
    value: PropTypes.string,
    name: PropTypes.string,
    checked: PropTypes.bool
}

function ToggleSwitch(props){
    const toggle = useRef();
    const checkbox = useRef();
    const [isPrivate, setIsPrivate] = useState(false);

    useEffect(() => {
      if (!props.checked) {
          toggle.current.classList.add('toggled');
          checkbox.current.checked = true;
      } else {
          toggle.current.classList.remove('toggled');
          checkbox.current.checked = false;
      }
      setIsPrivate(checkbox.current.checked);
  }, [props.checked]);

    function handleToggle(event) {
        toggle.current.classList.toggle('toggled');
        checkbox.current.checked = !checkbox.current.checked;
        setIsPrivate(checkbox.current.checked);
        if (props.onChange){
          event.target.id = props.id;
           props.onChange(event);
        }
        
    }
  return (
    <div>
     <label className='label'>{isPrivate ? 'Private' : 'Public'}</label>
      <input
        ref={checkbox}
        name={props.name}
        className='toggle-checkbox'
        type='checkbox'
        value={props.value || false}
      />
      <span
        ref={toggle}
        onClick={handleToggle}
        className={props.checked ? 'toggled toggle-switch' : 'toggle-switch'}
      >
        <span className='toggle'></span>
      </span>
    </div>
  );
}


  export default ToggleSwitch;
