package com.javatech.lab4.services.hateoas.assemblers;

import com.javatech.lab4.services.dtos.StudentDto;
import com.javatech.lab4.services.hateoas.resources.StudentResource;
import com.javatech.lab4.web.versions.v1.StudentController;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StudentResourceAssembler extends RepresentationModelAssemblerSupport<StudentDto, StudentResource> {

    public StudentResourceAssembler() {
        super(StudentController.class, StudentResource.class);
    }

    @Override
    public StudentResource toModel(StudentDto studentDto) {
        StudentResource studentResource = new StudentResource(studentDto);

        studentResource.add(linkTo(methodOn(StudentController.class).getStudent(studentDto.id(), null)).withSelfRel());
        studentResource.add(linkTo(methodOn(StudentController.class).getFilteredStudents(null, 1, 20)).withRel("students"));

        return studentResource;
    }

    public PagedModel<StudentResource> toPagedModel(Page<StudentDto> page) {
        PagedModel<StudentResource> studentResources = PagedModel.of(
                page.getContent().stream().map(this::toModel).collect(Collectors.toList()),
                new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements())
        );

        studentResources.add(linkTo(methodOn(StudentController.class)
                .getFilteredStudents(null, page.getNumber(), page.getSize())).withSelfRel());

        return studentResources;
    }
}
