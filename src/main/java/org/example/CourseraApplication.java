package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class CourseraApplication extends Application<CourseraConfiguration> {

    public static void main(final String[] args) throws Exception {
        new CourseraApplication().run(args);
    }

    @Override
    public String getName() {
        return "Coursera";
    }

    @Override
    public void initialize(final Bootstrap<CourseraConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final CourseraConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
