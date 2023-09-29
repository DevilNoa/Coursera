package org.example.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.Courses;
import org.example.services.CourseService;
import org.example.services.JwtService;
import org.example.services.UserService;

import java.sql.SQLException;
import java.util.List;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResponse {

    private final CourseService courseService;

    public CourseResponse(CourseService courseService, UserService userService) {
        this.courseService = courseService;
    }

    // Endpoint to get all courses as a list
    @GET
    public String getAllCoursesAsList() {
        return courseService.getAllCoursesASList().toString();
    }

    // Endpoint to add a new course
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCourse(Courses newCourse) throws SQLException {
        courseService.addCourse(newCourse);
    }

    // Endpoint to get a course by ID
    @GET
    @Path("/{id}")
    public String getCourseById(@PathParam("id") int courseId) {
        Courses course = courseService.getCourseByID(courseId);
        if (course != null) {
            return course.toString();
        } else {
            return "Course not found with ID: " + courseId;
        }
    }

    // Endpoint to update a course by ID
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCourse(@PathParam("id") int id, Courses updatedCourse) {
        Courses course = courseService.updateCourse(id, updatedCourse);

        if (course != null) {
            return Response.ok(course).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Course not found")
                    .build();
        }
    }

    // Endpoint to delete a course by ID
    @DELETE
    @Path("/{id}")
    public Response deleteCourse(@PathParam("id") int courseId) {
        boolean success = courseService.deleteCourse(courseId);
        if (success) {
            return Response.ok("Course deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Course not found or deletion failed")
                    .build();
        }
    }

    // Endpoint to get all courses with authentication
    @GET
    @PermitAll
    @Path("/all-courses-with-login")
    public Response getAllCoursesAsList(@HeaderParam("Authorization") String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                List<Courses> courses = courseService.getAllCoursesASList();
                return Response.ok(courses).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }


    //Endpoint to create a new course with authentication
    @POST
    @Path("/new-course-login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCourse(@HeaderParam("Authorization") String token, Courses newCourse) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                courseService.addCourse(newCourse);
                return Response.status(Response.Status.CREATED).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException | SQLException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    // Endpoint to update a course by id
    @PUT
    @Path("/update-course-login/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCourse(@HeaderParam("Authorization") String token, @PathParam("id") int id, Courses updatedCourse) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                Courses course = courseService.updateCourse(id, updatedCourse);

                if (course != null) {
                    return Response.ok(course).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Course not found")
                            .build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    //Endpoint to delete a course by id
    @DELETE
    @Path("/delete-course-login/{id}")
    public Response deleteCourse(@HeaderParam("Authorization") String token, @PathParam("id") int courseId) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String jwtToken = token.substring("Bearer ".length()).trim();
            if (JwtService.verifyToken(jwtToken)) {
                boolean success = courseService.deleteCourse(courseId);
                if (success) {
                    return Response.ok("Course deleted successfully").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Course not found or deletion failed")
                            .build();
                }
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
