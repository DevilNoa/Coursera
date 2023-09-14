package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
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
import org.example.security.JwtAuthenticationFilter;
import org.example.services.CourseService;
import org.example.services.InstructorService;
import org.example.services.StudentService;
import org.example.services.UserService;


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
    }

    @Override
    public void run(final CourseraConfiguration configuration, final Environment environment) {

        final JwtConfiguration jwtConfig = configuration.getJwtConfiguration();
        final JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtConfig.getSecretKey());
        environment.jersey().register(jwtFilter);

        final StudentDatabase studentDatabase = new StudentDatabase(configuration.getConnection());
        final StudentService studentService = new StudentService(studentDatabase);

        environment.jersey().register(new StudentResponse(studentService));

        final InstructorDatabase instructorDatabase = new InstructorDatabase(configuration.getConnection());
        final InstructorService instructorService = new InstructorService(instructorDatabase);

        environment.jersey().register(new InstructorResponse(instructorService));

        final CoursesDatabase coursesDatabase = new CoursesDatabase(configuration.getConnection());
        final CourseService courseService = new CourseService(coursesDatabase);

        environment.jersey().register(new CourseResponse(courseService));

        final UserDatabase userDatabase = new UserDatabase(configuration.getConnection());
        final UserService userService = new UserService(userDatabase);
        environment.jersey().register(new UserResponse(userService));
    }

}
