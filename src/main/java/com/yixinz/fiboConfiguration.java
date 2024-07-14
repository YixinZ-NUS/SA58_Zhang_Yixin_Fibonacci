package com.yixinz;

import io.dropwizard.core.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.*;
import jakarta.validation.constraints.*;

public class fiboConfiguration extends Configuration {
    @NotEmpty
    private double[] coinsToUse;

    private final double[] allCoins = new double[]{0.01, 0.05, 0.1, 0.2, 0.5, 1, 2, 5, 10, 50, 100, 1000};

    @JsonProperty
    public double[] getCoinsToUse() {
        return coinsToUse;
    }

    @JsonProperty
    public void setCoinsToUse(double[] coinsToUse) {
        this.coinsToUse = coinsToUse;
    }

    @JsonProperty
    public double[] getAllCoins() {
        return allCoins;
    }
}
