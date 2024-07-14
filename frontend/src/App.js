import React, { useState } from 'react';
import CoinDenoForm from './CoinDenoForm';
import axios from 'axios';
import './App.css';

function App() {
  const [response, setResponse] = useState(null);

  const handleCalculate = async (targetAmt, coinsToUse) => {
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
        <CoinDenoForm onCalculate={handleCalculate} />
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