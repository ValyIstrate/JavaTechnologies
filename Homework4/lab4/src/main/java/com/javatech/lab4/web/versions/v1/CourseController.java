package com.javatech.lab4.web.versions.v1;

import com.javatech.lab4.services.CourseService;
import com.javatech.lab4.services.dtos.CourseDto;
import com.javatech.lab4.web.requests.FilterCoursesRequests;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Tag(name = "Courses", description = "Course Controller")
public class CourseController {

    private final CourseService courseService;

    @PostMapping(path = "/filter",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Filter Courses",
            tags = {"Courses"},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<Page<CourseDto>> getFilteredCourses(@RequestBody FilterCoursesRequests filter,
                                                                        @RequestParam(name = "page") int page,
                                                                        @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(
                courseService.filterCourses(filter, page, size),
                HttpStatus.OK
        );
    }
}
