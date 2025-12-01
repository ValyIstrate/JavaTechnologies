package com.javatech.lab4.web.versions.v1;

import com.javatech.lab4.services.StudentGradeService;
import com.javatech.lab4.web.responses.StudentGradesRs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;

@Controller
@RequestMapping("/api/v1/student-grades")
@RequiredArgsConstructor
@Tag(name = "StudentGrades", description = "Student-Grades Controller")
public class StudentGradeController {

    private final StudentGradeService studentGradeService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @GetMapping(path = "/",  produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Get grades for student and/or course",
            tags = {"StudentGrades"},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<StudentGradesRs> getStudentGrades(@RequestParam(required = false) String studentCode,
                                                      @RequestParam(required = false) String courseCode) {
        return new ResponseEntity<>(
                studentGradeService.getGradesByStudentCodeAndCourseCode(studentCode, courseCode),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @PostMapping(path = "/from-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            description = "Post grades from CSV file",
            tags = {"StudentGrades"},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<Void> postGradesFromCsv(@RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        studentGradeService.parseAndSaveCsv(file);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
