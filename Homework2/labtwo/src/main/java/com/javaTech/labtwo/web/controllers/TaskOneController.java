package com.javaTech.labtwo.web.controllers;

import com.javaTech.labtwo.services.TaskOneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class TaskOneController {

    private final TaskOneService taskOneService;

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomersLastUpdatesDates() {
        return new ResponseEntity<>(
                taskOneService.getMessage(),
                HttpStatus.OK
        );
    }
}
