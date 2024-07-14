package com.yixinz.resources;

import com.codahale.metrics.annotation.Timed;
import com.yixinz.api.CoinDenominations;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.DoubleStream;

@Path("/find")
@Produces(MediaType.APPLICATION_JSON)
// for now, use find?amt=10000
public class CalcResource {
    //TODO: Implicitly pass targetAmt
    //private double targetAmt;
    @NotEmpty
    private double[] coinsToUse;
    private final AtomicLong counter;

    public CalcResource() {
        this.coinsToUse= new double[]{};
        // TODO: change later to fit user's choice; how to initialize coins?
        // *100 to all values to avoid 0.3000000004 issue
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public CoinDenominations WrapRes(@QueryParam("amt") double targetAmt, double[] coins) {
        coins = this.coinsToUse; // to change later
        double[] res = Calc((int)(targetAmt*100),getCoinsIntForCalc(coins)).stream().
                mapToDouble(Integer::doubleValue).
                map(x->x/100).
                toArray();
        return new CoinDenominations(counter.incrementAndGet(), res);
    }

    //TODO: reason why not using recursive (and BFS?) -> hard to maintain new array recording coins used?
    /*
    returns the list of denominations used to achieve the minimum number of coins for the target amount.
    time complexity O(targetAmt * n), where n is the number of coin denominations.
    space complexity O(targetAmt)
    */
    public ArrayList<Integer> Calc(int targetAmt, int[] coins){
                int max = targetAmt + 1;
                //dp[i] holds the min coins required for amount i.
                //dp[targetAmt] contains the minimum number of coins required to make up the target amount.
                //prev[i] holds the previous state for optimal solution at i
                int[] dp = new int[targetAmt + 1];
                int[] prev = new int[targetAmt + 1];

                // Initialize dp array with max value except dp[0] which is 0
                Arrays.fill(dp, max);
                dp[0] = 0;

                for (int i = 1; i <= targetAmt; i++) {
                    for (int coin : coins) {
                        if (coin <= i) {
                            if (dp[i] > dp[i - coin] + 1) {
                                //If current coin can be used and achieves better result,
                                //Update the minimum coins required and the previous state
                                dp[i] = dp[i - coin] + 1;
                                prev[i] = i - coin;
                            }
                        }
                    }
                }

                if (dp[targetAmt] > targetAmt) {
                    return new ArrayList<>();
                }
                //Reconstructing the list of denominations, backtrack from targetAmt to 0

                ArrayList<Integer> result = new ArrayList<>();
                while (targetAmt > 0) {
                    result.add(targetAmt - prev[targetAmt]);// Add the denomination used
                    targetAmt = prev[targetAmt];// Move to the previous state
                }
            return result;
        }

    public int[] getCoinsIntForCalc(double[] coins){
        return DoubleStream.of(coins).
                map(x->x*100).
                mapToInt(x->(int)x).
                toArray();
    }
}