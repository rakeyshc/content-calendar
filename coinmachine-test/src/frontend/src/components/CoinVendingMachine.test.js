import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import CoinVendingMachine from "./CoinVendingMachine";
import CoinService from "../service/CoinService";

jest.mock("../service/CoinService");

describe("CoinVendingMachine", () => {
  test("renders CoinVendingMachine component", async () => {
    const coinsData = { "£2": 10, "£1": 20, "50p": 50, "20p": 100 };
    CoinService.fetchCoins.mockResolvedValue({ data: coinsData });

    render(<CoinVendingMachine />);

    expect(screen.getByText("Total Amount: £220.00")).toBeInTheDocument();
    expect(screen.getByText("Coins Available")).toBeInTheDocument();
    expect(screen.getByText("Add Coins")).toBeInTheDocument();
    expect(screen.getByPlaceholderText("Enter Amount")).toBeInTheDocument();
    expect(screen.getByText("Get Change")).toBeInTheDocument();

    fireEvent.click(screen.getByText("Add Coins"));
    expect(screen.getByText("Enter coin details:")).toBeInTheDocument();
    expect(screen.getByLabelText("£2")).toBeInTheDocument();
    expect(screen.getByLabelText("£1")).toBeInTheDocument();
    expect(screen.getByLabelText("50p")).toBeInTheDocument();
    expect(screen.getByLabelText("20p")).toBeInTheDocument();

    fireEvent.click(screen.getByText("Submit"));
    await waitFor(() => expect(CoinService.addCoins).toHaveBeenCalled());

    fireEvent.change(screen.getByPlaceholderText("Enter Amount"), {
      target: { value: "1.70" },
    });
    expect(screen.getByPlaceholderText("Enter Amount")).toHaveValue("1.70");

    fireEvent.click(screen.getByText("Get Change"));
    await waitFor(() => expect(CoinService.getChange).toHaveBeenCalled());
    expect(screen.getByText("Change returned")).toBeInTheDocument();
  });
});
