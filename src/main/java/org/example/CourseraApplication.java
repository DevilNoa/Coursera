package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.example.services.InstructorService;
import org.example.services.StudentService;


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

        final StudentService studentService = new StudentService();
        final InstructorService instructorService = new InstructorService();

        environment.jersey().register(studentService);
        environment.jersey().register(instructorService);
    }

}
