package org.example.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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

}
