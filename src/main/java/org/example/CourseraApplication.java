package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import org.example.api.CourseResponse;
import org.example.api.InstructorResponse;
import org.example.api.StudentResponse;
import org.example.api.UserResponse;
import org.example.config.JwtConfiguration;
import org.example.db.CoursesDatabase;
import org.example.db.InstructorDatabase;
import org.example.db.StudentDatabase;
import org.example.db.UserDatabase;
import org.example.services.CourseService;
import org.example.services.InstructorService;
import org.example.services.StudentService;
import org.example.services.UserService;


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

        //Creating an API endpoints for the program and resources responsible for handling HTTP requests

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
        final UserService userService = new UserService(userDatabase);
        environment.jersey().register(new UserResponse(userService));
    }

}
