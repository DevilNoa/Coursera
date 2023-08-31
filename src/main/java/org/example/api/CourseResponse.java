package org.example.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.Courses;
import org.example.services.CourseService;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResponse {

    private final CourseService courseService;

    public CourseResponse(CourseService courseDatabase) {
        this.courseService = courseDatabase;
    }

    @GET
    public String getAllCoursesAsList() {
        return courseService.getAllCoursesASList().toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCourse(Courses newCourse) {
        courseService.addCourse(newCourse);
    }

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
}
