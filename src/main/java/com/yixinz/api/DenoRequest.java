package com.yixinz.api;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class DenoRequest {
    @NotNull
    private double targetAmt;
    @NotNull
    private List<Double> coinsToUse;

    public DenoRequest() {}

    public DenoRequest(double targetAmt, List<Double> coinsToUse) {
        this.targetAmt = targetAmt;
        this.coinsToUse = coinsToUse;
    }

    public double getTargetAmt() {
        return targetAmt;
    }

    public void setTargetAmt(double targetAmt) {
        this.targetAmt = targetAmt;
    }

    public List<Double> getCoinsToUse() {
        return coinsToUse;
    }

    public void setCoinsToUse(List<Double> coinsToUse) {
        this.coinsToUse = coinsToUse;
    }
}
