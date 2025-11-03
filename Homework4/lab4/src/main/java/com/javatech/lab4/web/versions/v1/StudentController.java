package com.javatech.lab4.web.versions.v1;

import com.javatech.lab4.services.StudentService;
import com.javatech.lab4.services.dtos.StudentDto;
import com.javatech.lab4.services.hateoas.assemblers.StudentResourceAssembler;
import com.javatech.lab4.services.hateoas.resources.StudentResource;
import com.javatech.lab4.web.requests.CreateStudentPreferencesRequest;
import com.javatech.lab4.web.requests.CreateStudentRequest;
import com.javatech.lab4.web.requests.FilterStudentsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Students", description = "Student Controller")
public class StudentController {

    private final StudentService studentService;
    private final StudentResourceAssembler studentResourceAssembler;

    @PostMapping(path = "/",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Create Student",
            tags = {"Students"},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "CONFLICT")
    })
    public ResponseEntity<StudentResource> postStudent(@RequestBody CreateStudentRequest createStudentRequest) {
        return new ResponseEntity<>(
                studentResourceAssembler.toModel(studentService.createStudent(createStudentRequest)),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/filter",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Filter Students",
            tags = {"Students"},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<PagedModel<StudentResource>> getFilteredStudents(@RequestBody FilterStudentsRequest filter,
                                                                                    @RequestParam(name = "page") int page,
                                                                                    @RequestParam(name = "size") int size) {
        Page<StudentDto> responsePage = studentService.filterStudents(filter, page, size);
        PagedModel<StudentResource> studentResources = studentResourceAssembler.toPagedModel(responsePage);
        return new ResponseEntity<>(studentResources, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}",  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            description = "Get student by id",
            tags = {"Students"},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    public ResponseEntity<StudentResource> getStudent(@PathVariable(name = "id") Long id,
                                                      @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatchHeader) {
        // curl -X GET "http://localhost:8080/homework/api/v1/students/5" -H "Accept: application/xml"
        // http://localhost:8080/homework/swagger-ui/index.html#/
        var student = studentService.getStudent(id);
        String eTag = String.valueOf(student.hashCode());

        if (Objects.nonNull(ifNoneMatchHeader) && ifNoneMatchHeader.equals(eTag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
        }

        return ResponseEntity
                .ok()
                .eTag(eTag)
                .body(studentResourceAssembler.toModel(student));
    }

    @PutMapping(path = "/{id}/preferences", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Add student preferences",
            tags = {"Students"},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    public ResponseEntity<StudentResource> addStudentPreferences(@PathVariable(name = "id") Long id,
                                                                 @RequestBody CreateStudentPreferencesRequest createStudentPreferencesRequest) {
        return new ResponseEntity<>(
                studentResourceAssembler.toModel(studentService.addStudentPreferences(id, createStudentPreferencesRequest)),
                HttpStatus.OK
        );
    }
}
