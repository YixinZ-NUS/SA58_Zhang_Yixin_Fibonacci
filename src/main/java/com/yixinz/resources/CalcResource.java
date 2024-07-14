package com.yixinz.resources;

import com.codahale.metrics.annotation.Timed;
import com.yixinz.api.CoinDenominations;
import com.yixinz.api.DenoRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Path("/find")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
// for now, use find?amt=10000
public class CalcResource {
    private final AtomicLong counter;

    public CalcResource() {
        this.counter = new AtomicLong();
    }

    @POST
    @Timed
    public CoinDenominations WrapRes(@Valid DenoRequest request) {
        double targetAmt = request.getTargetAmt();
        List<Double> coins = request.getCoinsToUse();
        System.out.println("Received request: " + request);
        // *100 to all values to avoid 0.3000000004 issue
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

    public int[] getCoinsIntForCalc(List<Double> coins){
        return coins.stream().
                map(x->x*100).
                mapToInt(Double::intValue).
                toArray();
    }
}