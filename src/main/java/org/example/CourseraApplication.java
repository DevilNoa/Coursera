package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import org.example.api.*;
import org.example.config.JwtConfiguration;
import org.example.db.*;
import org.example.services.*;


public class CourseraApplication extends Application<CourseraConfiguration> {

    private UserService userService;

    public static void main(final String[] args) throws Exception {
        new CourseraApplication().run(args);
    }

    @Override
    public String getName() {
        return "Coursera";
    }

    @Override
    public void run(final CourseraConfiguration configuration, final Environment environment) {
        //JWT configuration for the application
        final JwtConfiguration jwtConfig = configuration.getJwtConfiguration();


        //Student endpoint
        final StudentDatabase studentDatabase = new StudentDatabase(configuration.getConnection());
        final StudentService studentService = new StudentService(studentDatabase);
        environment.jersey().register(new StudentResponse(studentService, userService));

        //Instructor endpoint
        final InstructorDatabase instructorDatabase = new InstructorDatabase(configuration.getConnection());
        final InstructorService instructorService = new InstructorService(instructorDatabase);
        environment.jersey().register(new InstructorResponse(instructorService, userService));

        //Courses endpoint
        final CoursesDatabase coursesDatabase = new CoursesDatabase(configuration.getConnection());
        final CourseService courseService = new CourseService(coursesDatabase);
        environment.jersey().register(new CourseResponse(courseService, userService));

        //User endpoint
        final UserDatabase userDatabase = new UserDatabase(configuration.getConnection());
        userService = new UserService(userDatabase);
        environment.jersey().register(new UserResponse(userService));
    }

}
