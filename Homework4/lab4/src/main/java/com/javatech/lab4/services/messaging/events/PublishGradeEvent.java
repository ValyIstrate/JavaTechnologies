package com.javatech.lab4.services.messaging.events;

import java.util.UUID;

public record PublishGradeEvent (
        UUID messageCode,
        String studentCode,
        String courseCode,
        Double grade
) {
}
