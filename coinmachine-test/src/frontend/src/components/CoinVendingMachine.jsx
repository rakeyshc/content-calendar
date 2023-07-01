import React, { useState, useEffect } from "react";
import CoinService from "../service/CoinService";
import DisplayCoins from "./DisplayCoins";
import CoinMachineLogo from "./CoinMachineLogo";
import Button from "./Button";
import CoinsForm from "./CoinsForm";

const CoinVendingMachine = () => {

  //States
  const [availableCoins, setAvailableCoins] = useState([]);
  const [change, setChange] = useState(0);
  const [amount, setAmount] = useState(0);
  const [showCoinsForm, setShowCoinsForm] = useState(false);
  const [showAvailableCoins, setShowAvailableCoins] = useState(false);


  // Life cycle hook to fetch the coins on each component mount.
  useEffect(() => {
    CoinService.fetchCoins()
      .then((response) => {
        setAvailableCoins(response.data);
        console.log(response.data);
        setShowAvailableCoins(true);
        setShowCoinsForm(false);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [change]);

  const handleUserInput = (e) => {
    e.preventDefault();
    const value = e.target.value;
    setAmount(value);
  };

  const handleTenderChange = (e) => {
    e.preventDefault();
    CoinService.getChange(amount)
      .then((response) => {
        setChange(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleShowCoinsForm = (e) => {
    e.preventDefault();
    setShowCoinsForm(true);
    setShowAvailableCoins(false);
  };

  const handleCoinsFormSubmit = (coins) => {
    setShowCoinsForm(false);
    CoinService.addCoins(coins)
      .then((response) => {
        setAvailableCoins(response.data);
        setShowAvailableCoins(true);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const totalAmount = Object.entries(availableCoins)
    .reduce((acc, [coin, count]) => {
      const value = Number(coin.replace(/[^\d]/g, ""));
      const isPound = coin.includes("£");
      return acc + (isPound ? value * count : (value * count) / 100);
    }, 0)
    .toFixed(2);

  return (
    <div className="bg-gray-700 text-white py-12">
      <div className="max-w-5xl mx-auto flex-row justify-start items-start">
        <div className="flex justify-center items-center mb-8">
          <h2 className="text-2xl font-bold">Total Amount: £{totalAmount}</h2>
        </div>
        <div className="flex items-start gap-12">
          {showAvailableCoins ? (
            <DisplayCoins coins={availableCoins} text="Coins Available" />
          ) : showCoinsForm ? (
            <CoinsForm onSubmit={handleCoinsFormSubmit} />
          ) : null}
          <div className="flex-col">
            <CoinMachineLogo />
            <div className="flex mt-16 ml-4 gap-2">
              <Button onClick={handleShowCoinsForm} text="Add Coins" />
              <input
                className="shadow-sm rounded-md text-blue-700 font-semibold"
                type="text"
                name="amount"
                placeholder="Enter Amount"
                onChange={handleUserInput}
                value={amount}
              />
              <Button onClick={handleTenderChange} text="Get Change" />
            </div>
          </div>
          <DisplayCoins coins={change} text="Change returned" />
        </div>
      </div>
    </div>
  );
};

export default CoinVendingMachine;
