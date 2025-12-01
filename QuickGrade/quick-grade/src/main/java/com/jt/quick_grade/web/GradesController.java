package com.jt.quick_grade.web;

import com.jt.quick_grade.services.GradeService;
import com.jt.quick_grade.web.requests.PostGradeRq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/grades")
@Tag(name = "Grades", description = "QuickGrades Controller")
public class GradesController {

    private final GradeService gradeService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR')")
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Post a Grade",
            tags = {"Grades"},
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT")
    })
    public ResponseEntity<Void> postGrade(@RequestBody PostGradeRq dto) {
        gradeService.postGrade(dto);
        return ResponseEntity.noContent().build();
    }
}
