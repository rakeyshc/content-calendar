import './App.css';
import Banner from './components/Banner';
import CoinVendingMachine from './components/CoinVendingMachine';

function App() {
  return (
    <div className="bg-gray-800 min-h-screen">
      <Banner/>
      <CoinVendingMachine/>
    </div>
  );
}

export default App;
