package com.yixinz.api;

import java.util.ArrayList;
import java.util.List;

public class CoinDenominations {
    private long id;
    private double[] coinDenominations;
    /* expected api:
    {
      "id": 1,
      "coinDenominations": "[0.1, 0.1, 1, 100]"
    }
     */
    public CoinDenominations() {}
    public CoinDenominations(long id, double[] coinDenominations) {
        this.id = id;
        this.coinDenominations = coinDenominations;
    }

    public long getId() {
        return id;
    }

    public double[] getCoinDenominations() {
        return coinDenominations;
    }
}
