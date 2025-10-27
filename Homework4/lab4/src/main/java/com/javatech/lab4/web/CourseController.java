package com.javatech.lab4.web;

import com.javatech.lab4.services.CourseService;
import com.javatech.lab4.services.dtos.CourseDto;
import com.javatech.lab4.web.requests.FilterCoursesRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping(path = "/filter",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CourseDto>> getCustomersLastUpdatesDates(@RequestBody FilterCoursesRequests filter,
                                                                        @RequestParam(name = "page") int page,
                                                                        @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(
                courseService.filterCourses(filter, page, size),
                HttpStatus.OK
        );
    }
}
