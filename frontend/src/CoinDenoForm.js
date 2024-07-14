import React, { useState } from 'react';

const availableCoins = [
    { value: 0.01, label: '1 Cent' },
    { value: 0.05, label: '5 Cents' },
    { value: 0.1, label: '10 Cents' },
    { value: 0.2, label: '20 Cents' },
    { value: 0.5, label: '50 Cents' },
    { value: 1, label: '1 Dollar' },
    { value: 2, label: '2 Dollars' },
    { value: 5, label: '5 Dollars' },
    { value: 10, label: '10 Dollars' },
    { value: 50, label: '50 Dollars' },
    { value: 100, label: '100 Dollars' },
    { value: 1000, label: '1000 Dollars' },
];

const CoinDenoForm = ({ onCalculate }) => {
    const [targetAmt, setTargetAmt] = useState('');
    const [selectedCoins, setSelectedCoins] = useState([]);

    const handleCoinChange = (event, coinValue) => {
        if (event.target.checked) {
            setSelectedCoins(prevCoins => [...prevCoins, coinValue]);
        } else {
            setSelectedCoins(prevCoins => prevCoins.filter(coin => coin !== coinValue));
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        onCalculate(targetAmt, selectedCoins);
    };

    return (
        <div>
            <h2>Coin denominations Calculator</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Target Amount:
                    <input type="number" value={targetAmt} onChange={e => setTargetAmt(e.target.value)}/>
                </label>
                <br/>
                <h3>Select Coin Denominations:</h3>
                    <div className="coin-selection">
                    {availableCoins.map(coin => (
                        <div key={coin.value}>
                            <input
                                type="checkbox"
                                checked={selectedCoins.includes(coin.value)}
                                onChange={e => handleCoinChange(e, coin.value)}
                            />
                            <label>{coin.label}</label>
                        </div>
                    ))}
                    </div>
                <br/>
                <button type="submit">Calculate</button>
            </form>
        </div>
);
};

export default CoinDenoForm;