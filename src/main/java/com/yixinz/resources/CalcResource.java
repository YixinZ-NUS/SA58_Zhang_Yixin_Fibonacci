package com.yixinz.resources;

import com.codahale.metrics.annotation.Timed;
import com.yixinz.api.CoinDenominations;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Path("/find")
@Produces(MediaType.APPLICATION_JSON)
// for now, use find?amt=10000
public class CalcResource {
    private final int[] coins;
    //private final int[] defaultCoins;
    //TODO: change way to pass value, utilizing configuration, 'Getting Started- Registering A Resource'

    //TODO: Implicitly pass targetAmt
    //private double targetAmt;
    private final AtomicLong counter;

    public CalcResource() {

        //this.coins = coins;
        this.coins= new int[]{1, 5, 10, 20, 50, 100, 200, 500, 1000, 5000, 10000, 100000};
        // TODO: change later to fit user's choice; how to initialize coins?
        //this.defaultCoins = new int[]{1, 5, 10, 20, 50, 100, 200, 500, 1000, 5000, 10000, 100000};
        // *100 to all values to avoid 0.3000000004 issue
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public CoinDenominations WrapRes(@QueryParam("amt") double targetAmt) {
        double[] res = Calc((int)(targetAmt*100)).stream().
                mapToDouble(Integer::doubleValue).
                map(x->x/100).
                toArray();
        return new CoinDenominations(counter.incrementAndGet(), res);
    }

    //TODO: reason why not using recursive (and BFS?) -> hard to maintain new array recording coins used?
    public ArrayList<Integer> Calc(int targetAmt){
                int max = targetAmt + 1;
                int[] dp = new int[targetAmt + 1];
                int[] prev = new int[targetAmt + 1];
                Arrays.fill(dp, max);
                dp[0] = 0;

                for (int i = 1; i <= targetAmt; i++) {
                    for (int coin : coins) {
                        if (coin <= i) {
                            if (dp[i] > dp[i - coin] + 1) {
                                dp[i] = dp[i - coin] + 1;
                                prev[i] = i - coin; // previous state
                            }
                        }
                    }
                }

                if (dp[targetAmt] > targetAmt) {
                    return new ArrayList<>();
                }

                ArrayList<Integer> result = new ArrayList<>();
                while (targetAmt > 0) {
                    result.add(targetAmt - prev[targetAmt]);
                    targetAmt = prev[targetAmt];
                }
            return result;
        }
    }