import React from 'react'

const Button = ({ onClick, text }) => (
  <button
    onClick={onClick}
    className='rounded text-white font-semibold bg-blue-700 px-5 py-3'
  >
    {text}
  </button>
);

export default Button;
