package com.javaTech.labtwo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskOneService {

    @Value("${task-one-message}")
    private String taskOneMessage;

    public String getMessage() {
        return taskOneMessage;
    }
}
