package com.jt.quick_grade.services.messaging.events;

import java.util.UUID;

public record PublishGradeEvent(
        UUID messageCode,
        String studentCode,
        String courseCode,
        Double grade
) {
}
