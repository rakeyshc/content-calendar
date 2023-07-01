import React, { useState } from "react";
import CoinInput from "./CoinInput";

const CoinsForm = (props) => {
  const [formState, setFormState] = useState({
    twoPounds: 0,
    onePound: 0,
    fiftyPence: 0,
    twentyPence: 0,
    tenPence: 0,
    fivePence: 0,
    twoPence: 0,
    onePence: 0,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormState({
      ...formState,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = {
      twoPounds: formState.twoPounds,
      onePound: formState.onePound,
      fiftyPence: formState.fiftyPence,
      twentyPence: formState.twentyPence,
      tenPence: formState.tenPence,
      fivePence: formState.fivePence,
      twoPence: formState.twoPence,
      onePence: formState.onePence,
    };

    const convertedData = {
      "\u00A32": data.twoPounds,
      "\u00A31": data.onePound,
      "50p": data.fiftyPence,
      "20p": data.twentyPence,
      "10p": data.tenPence,
      "5p": data.fivePence,
      "2p": data.twoPence,
      "1p": data.onePence,
    };
    props.onSubmit(convertedData);
  };

  return (
    <div className="bg-violet-900 w-52 rounded-lg overflow-hidden">
      <form
        onSubmit={handleSubmit}
        style={{
          display: "flex",
          flexDirection: "row",
          alignItems: "center",
          justifyContent: "flex-end",
          flexWrap: "wrap",
          marginRight: "1em",
          marginLeft: "1em",
          marginTop: "0.5em",
        }}
      >
        <CoinInput
          label="£2"
          name="twoPounds"
          value={formState.twoPounds}
          onChange={handleChange}
        />
        <CoinInput
          label="£1"
          name="onePound"
          value={formState.onePound}
          onChange={handleChange}
        />
        <CoinInput
          label="50p"
          name="fiftyPence"
          value={formState.fiftyPence}
          onChange={handleChange}
        />
        <CoinInput
          label="20p"
          name="twentyPence"
          value={formState.twentyPence}
          onChange={handleChange}
        />
        <CoinInput
          label="10p"
          name="tenPence"
          value={formState.tenPence}
          onChange={handleChange}
        />
        <CoinInput
          label="5p"
          name="fivePence"
          value={formState.fivePence}
          onChange={handleChange}
        />
        <CoinInput
          label="2p"
          name="twoPence"
          value={formState.twoPence}
          onChange={handleChange}
        />
        <CoinInput
          label="1p"
          name="onePence"
          value={formState.onePence}
          onChange={handleChange}
        />
        <button
          type="submit"
          className="w-24 px-3 ml-4 py-2 rounded-md bg-blue-500 text-white"
        >
          Submit
        </button>
      </form>
    </div>
  );
};

export default CoinsForm;
