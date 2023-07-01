import axios from "axios";

const COIN_SERVICE_BASE_URL = "http://localhost:8089/api/v1";


class CoinService{

    fetchCoins() {
        return axios.get(COIN_SERVICE_BASE_URL+'/coins');
    }

    getChange(amount) {
        return axios.get(COIN_SERVICE_BASE_URL+`/dispense/${amount}`)
    }

    addCoins(data){
        return axios.post(COIN_SERVICE_BASE_URL+'/coins', data)
    }
}

export default new CoinService();