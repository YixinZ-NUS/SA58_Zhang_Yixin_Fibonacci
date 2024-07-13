package com.yixinz.health;
import com.codahale.metrics.health.HealthCheck;

public class basicHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
