package com.yixinz;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class fiboApplication extends Application<fiboConfiguration> {

    public static void main(final String[] args) throws Exception {
        new fiboApplication().run(args);
    }

    @Override
    public String getName() {
        return "fibo";
    }

    @Override
    public void initialize(final Bootstrap<fiboConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final fiboConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
