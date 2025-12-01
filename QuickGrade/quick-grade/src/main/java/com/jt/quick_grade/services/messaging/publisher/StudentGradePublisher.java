package com.jt.quick_grade.services.messaging.publisher;

import com.jt.quick_grade.services.messaging.events.PublishGradeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentGradePublisher {

    private final KafkaTemplate<String, PublishGradeEvent> kafkaTemplate;

    public PublishGradeEvent buildEvent(String studentCode, String courseCode, Double grade) {
        return new PublishGradeEvent(UUID.randomUUID(), studentCode, courseCode, grade);
    }

    public void publish(PublishGradeEvent event) {
        log.info("Publish student grade event: {}", event);
        kafkaTemplate.send("grades-topic", event);
    }
}
