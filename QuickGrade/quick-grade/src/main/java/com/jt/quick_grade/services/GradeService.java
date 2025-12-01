package com.jt.quick_grade.services;

import com.jt.quick_grade.services.messaging.publisher.StudentGradePublisher;
import com.jt.quick_grade.web.requests.PostGradeRq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final StudentGradePublisher studentGradePublisher;


    public void postGrade(PostGradeRq dto) {
        var postGradeEvent = studentGradePublisher
                .buildEvent(dto.studentCode(), dto.courseCode(), dto.grade());

        studentGradePublisher.publish(postGradeEvent);
    }
}
