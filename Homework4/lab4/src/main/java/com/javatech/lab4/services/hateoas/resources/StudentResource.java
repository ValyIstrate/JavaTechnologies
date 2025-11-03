package com.javatech.lab4.services.hateoas.resources;

import com.javatech.lab4.services.dtos.StudentDto;
import org.springframework.hateoas.EntityModel;

public class StudentResource extends EntityModel<StudentDto> {
    public StudentResource(StudentDto student) {
        super(student);
    }
}
