package com.yixinz.resources;

import com.yixinz.api.CoinDenominations;
import com.yixinz.api.DenoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CalcResourceTest {

    private CalcResource calcResource;

    @BeforeEach
    void setUp() {
        calcResource = new CalcResource();
    }
    @Test
    public void testWrapResForValidCoinDenominations1() {
        Double[] deno = {0.1, 0.5, 1.0, 5.0, 10.0};
        // 1.0 (not 1) doesn't matter since will *100 and everything becomes int,
        // not verified but hopefully solves the issue of 0.1+0.2=0.3000..04
        ArrayList<Double> denominationsList = new ArrayList<>(Arrays.asList(deno));
        DenoRequest request = new DenoRequest(7.3,denominationsList);
        //example in the requirement has a typo, 7.03 ->7.3
        CoinDenominations response = calcResource.WrapRes(request);

        assertArrayEquals(new double[]{0.1, 0.1, 0.1, 1, 1, 5}, response.getCoinDenominations());
    }
    @Test
    public void testWrapResForValidCoinDenominations2() {
        Double[] deno = {1.0, 2.0, 50.0};
        ArrayList<Double> denominationsList = new ArrayList<>(Arrays.asList(deno));
        DenoRequest request = new DenoRequest(103,denominationsList);
        CoinDenominations response = calcResource.WrapRes(request);

        assertArrayEquals(new double[]{1, 2, 50, 50}, response.getCoinDenominations());
    }
    @Test
    public void testWrapResForValidCoinDenominations3() {
        Double[] deno = {0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0};
        ArrayList<Double> denominationsList = new ArrayList<>(Arrays.asList(deno));
        DenoRequest request = new DenoRequest(1569.77,denominationsList);
        CoinDenominations response = calcResource.WrapRes(request);

        assertArrayEquals(new double[]{0.01, 0.01, 0.05, 0.2, 0.5, 2,
                2, 5, 10, 50, 100, 100, 100, 100, 100, 1000}, response.getCoinDenominations());
    }

    @Test
    void testWrapResForPartialCoinDenominations() {
        Double[] deno = {0.05, 0.5, 1.0, 5.0, 100.0, 1000.0};
        ArrayList<Double> denominationsList = new ArrayList<>(Arrays.asList(deno));
        DenoRequest request = new DenoRequest(3657.90,denominationsList);
        CoinDenominations response = calcResource.WrapRes(request);

        assertArrayEquals(new double[]{0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.5,
                1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 100, 100, 100, 100, 100, 100,
                1000, 1000, 1000}, response.getCoinDenominations());
    }
    @Test
    void testWrapResForNotAchievableCoinDenominations() {
        Double[] deno = {0.05, 0.5, 1.0, 5.0, 100.0, 1000.0};
        ArrayList<Double> denominationsList = new ArrayList<>(Arrays.asList(deno));
        DenoRequest request = new DenoRequest(6.99,denominationsList);
        CoinDenominations response = calcResource.WrapRes(request);

        assertArrayEquals(new double[]{}, response.getCoinDenominations());
    }

    @Test
    void testWrapResForZeroTarget() {
        Double[] deno = {0.01, 0.05, 50.0, 100.0, 1000.0};
        ArrayList<Double> denominationsList = new ArrayList<>(Arrays.asList(deno));
        DenoRequest request = new DenoRequest(0,denominationsList);
        CoinDenominations response = calcResource.WrapRes(request);

        assertArrayEquals(new double[]{}, response.getCoinDenominations());
    }

    // validators against invalid input (like negative targetAmt) is handled at front-end
}