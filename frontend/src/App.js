import React, { useState } from 'react';
import axios from 'axios';

function App() {
  const [targetAmt, setTargetAmt] = useState('');
  const [coinsToUse, setCoinsToUse] = useState([]);
  const [response, setResponse] = useState(null);

  const handleCoinChange = (event, coin) => {
    if (event.target.checked) {
      setCoinsToUse([...coinsToUse, coin]);
    } else {
      setCoinsToUse(coinsToUse.filter(c => c !== coin));
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const requestBody = {
      targetAmt: parseFloat(targetAmt),
      coinsToUse: coinsToUse,
    };
    try {
      const response = await axios.post('/find', requestBody);
      setResponse(response.data);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
      <div className="App">
        <h1>Coin Change Calculator</h1>
        <form onSubmit={handleSubmit}>
          <label>
            Target Amount:
            <input type="number" value={targetAmt} onChange={e => setTargetAmt(e.target.value)} />
          </label>
          <br />
          <h3>Select Coin Denominations:</h3>
          {['1.0', '2.0', '5.0'].map(coin => (
              <div key={coin}>
                <input type="checkbox" checked={coinsToUse.includes(coin)} onChange={e => handleCoinChange(e, coin)} />
                <label>{coin} Coin</label>
              </div>
          ))}
          <br />
          <button type="submit">Calculate</button>
        </form>
        {response && (
            <div>
              <h2>Result:</h2>
              <pre>{JSON.stringify(response, null, 2)}</pre>
            </div>
        )}
      </div>
  );
}

export default App;