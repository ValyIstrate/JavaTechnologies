package com.javatech.lab4.services;

import com.javatech.lab4.persistence.repositories.CourseRepository;
import com.javatech.lab4.persistence.specifications.CourseSpecification;
import com.javatech.lab4.services.dtos.CourseDto;
import com.javatech.lab4.web.requests.FilterCoursesRequests;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;

    public Page<CourseDto> filterCourses(FilterCoursesRequests filter, int page, int size) {
        return courseRepository.findAll(
                        CourseSpecification.filterCourses(filter),
                        PageRequest.of(page - 1, size))
                .map(CourseDto::fromEntity);
    }
}
