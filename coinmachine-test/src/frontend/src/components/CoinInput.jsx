import React from "react";
import PropTypes from 'prop-types';


const CoinInput = ({ label, name, value, onChange }) => {
  return (
    <label className="mb-3 ml-4">
      {label}:
      <input
        className="w-24 px-3 ml-4 py-2 rounded-md border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 text-blue-600 font-semibold"
        type="text"
        name={name}
        value={value}
        onChange={onChange}
        style={{ marginLeft: "15px" }}
      />
    </label>
  );
};

CoinInput.propTypes = {
    label: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    onChange: PropTypes.func.isRequired,
  };

export default CoinInput;