package com.yixinz.api;

import java.util.ArrayList;
import java.util.List;

public class CoinDenominations {
    private long id;
    private ArrayList<Integer> coinDenominations;
    /* expected api:
    {
      "id": 1,
      "coinDenominations": "[0.1, 0.1, 1, 100]"
    }
     */
    public CoinDenominations() {}
    public CoinDenominations(long id, ArrayList<Integer> coinDenominations) {
        this.id = id;
        this.coinDenominations = coinDenominations;
    }

    public long getId() {
        return id;
    }

    public ArrayList<Integer> getCoinDenominations() {
        return coinDenominations;
    }
}
