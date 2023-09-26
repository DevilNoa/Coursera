package org.example.api;

import io.dropwizard.auth.Auth;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.Courses;
import org.example.core.User;
import org.example.services.CourseService;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResponse {

    private final CourseService courseService;

    public CourseResponse(CourseService courseDatabase) {
        this.courseService = courseDatabase;
    }

    // Endpoint to get all courses as a list
    @GET
    public String getAllCoursesAsList() {
        return courseService.getAllCoursesASList().toString();
    }

    // Endpoint to add a new course
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCourse(Courses newCourse) {
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
    public Response getAllCoursesAsList(@Auth User authenticatedUser) {

        if (authenticatedUser != null) {
            return Response.ok(courseService.getAllCoursesASList()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();

        }
    }
}
