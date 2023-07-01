import React, { useEffect } from "react";
import Coin from "./Coin";

const DisplayCoins = (props) => {
  const coinArray = Object.entries(props.coins);
  useEffect(() => {}, []);

  return (
    <div className="bg-slate-900 w-52 rounded-lg overflow-hidden">
      <div className="flex items-center justify-between bg-slate-500 text-white py-2 px-3">
        <h3 className="text-lg px-2 font-medium">{props.text}</h3>
      </div>
      <div className="bg-slate-800 py-3 px-2 space-y-2">
        {coinArray.map((coin, index) => (
          <div className="bg-slate-800 rounded-md py-1 px-2 text-center" key={index}>
            <Coin value={coin[0]} count={coin[1]} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default DisplayCoins;
