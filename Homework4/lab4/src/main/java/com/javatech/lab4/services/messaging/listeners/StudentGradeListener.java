package com.javatech.lab4.services.messaging.listeners;

import com.javatech.lab4.persistence.entities.StudentGrades;
import com.javatech.lab4.persistence.repositories.CourseRepository;
import com.javatech.lab4.persistence.repositories.StudentGradesRepository;
import com.javatech.lab4.persistence.repositories.StudentRepository;
import com.javatech.lab4.services.messaging.events.PublishGradeEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentGradeListener {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentGradesRepository studentGradesRepository;

    @KafkaListener(topics = "grades-topic", groupId = "prefSchedule-new-001", containerFactory = "kafkaListenerFactory")
    @Transactional
    public void listen(PublishGradeEvent event) {
        log.info("Received grade event: {}", event);

        if (event.courseCode() == null || event.studentCode() == null || event.grade() == null) {
            log.error("Message <{}>: Event is missing required codes or grade. Skipping.", event.messageCode());
            return;
        }

        var course = courseRepository.findByCode(event.courseCode());
        if (course.isEmpty()) {
            log.warn("Message <{}>: Course {} not found", event.messageCode(), event.courseCode());
            return;
        }

        var presentCourse = course.get();
        if (!presentCourse.getType().equalsIgnoreCase("COMPULSORY")) {
            log.info("Message <{}>: Course {} is not a compulsory course", event.messageCode(), presentCourse.getCode());
            return;
        }

        var student = studentRepository.findByCode(event.studentCode());
        if (student.isEmpty()) {
            log.warn("Message <{}>: Student {} not found", event.messageCode(), event.studentCode());
            return;
        }

        studentGradesRepository.save(new StudentGrades(student.get(), presentCourse, event.grade()));
        log.info("Message <{}>: Consumed successfully!", event.messageCode());
    }

    @KafkaListener(topics = "grades-topic.DLT", groupId = "prefSchedule-new-002")
    public void listenDlq(PublishGradeEvent event) {
        log.warn("Sent to DLQ: {}", event.messageCode());
    }
}
