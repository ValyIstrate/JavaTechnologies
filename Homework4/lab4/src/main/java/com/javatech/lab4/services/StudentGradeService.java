package com.javatech.lab4.services;

import com.javatech.lab4.persistence.entities.StudentGrades;
import com.javatech.lab4.persistence.repositories.CourseRepository;
import com.javatech.lab4.persistence.repositories.StudentGradesRepository;
import com.javatech.lab4.persistence.repositories.StudentRepository;
import com.javatech.lab4.services.exceptions.FileProcessingException;
import com.javatech.lab4.web.responses.StudentGradesRs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentGradeService {

    private final StudentGradesRepository studentGradesRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    public StudentGradesRs getGradesByStudentCodeAndCourseCode(String studentCode, String courseCode) {
        return new StudentGradesRs(studentGradesRepository.findAllByStudentCodeAndCourseCode(studentCode, courseCode)
                .stream()
                .map(studentGrade ->
                        new StudentGradesRs.StudentGrade(studentGrade.getStudent().getCode(),
                                studentGrade.getCourse().getCode(),
                                studentGrade.getGrade()))
                .toList());
    }

    private record CsvGradeData(String studentCode, String courseCode, Double grade) {}

    @Transactional
    public void parseAndSaveCsv(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                Optional<CsvGradeData> gradeDataOpt = parseCsvLine(line, lineNumber);
                if (gradeDataOpt.isEmpty()) {
                    continue;
                }
                CsvGradeData gradeData = gradeDataOpt.get();

                var course = courseRepository.findByCode(gradeData.courseCode());
                if (course.isEmpty()) {
                    log.warn("Line {}: Course {} not found. Skipping.", lineNumber, gradeData.courseCode());
                    continue;
                }

                var presentCourse = course.get();
                if (!presentCourse.getType().equalsIgnoreCase("COMPULSORY")) {
                    log.info("Line {}: Course {} is not a compulsory course. Skipping.", lineNumber, presentCourse.getCode());
                    continue;
                }

                var student = studentRepository.findByCode(gradeData.studentCode());
                if (student.isEmpty()) {
                    log.warn("Line {}: Student {} not found. Skipping.", lineNumber, gradeData.studentCode());
                    continue;
                }

                studentGradesRepository.save(new StudentGrades(
                        student.get(),
                        presentCourse,
                        gradeData.grade()
                ));
                log.debug("Line {}: Grade saved for Student {} in Course {}", lineNumber, gradeData.studentCode(), gradeData.courseCode());
            }
        } catch (IOException e) {
            log.error("Error reading the uploaded file: {}", e.getMessage());
            throw new FileProcessingException(
                    String.format("Failed to read the uploaded CSV file. Reason: %s", e.getMessage()));
        }
    }

    private Optional<CsvGradeData> parseCsvLine(String line, int lineNumber) {
        String[] parts = line.split(",");

        if (parts.length != 3) {
            log.warn("Line {}: Invalid format. Expected 3 fields, found {}. Line: {}", lineNumber, parts.length, line);
            return Optional.empty();
        }

        try {
            String studentCode = parts[0].trim();
            String courseCode = parts[1].trim();
            Double grade = Double.parseDouble(parts[2].trim());

            if (studentCode.isEmpty() || courseCode.isEmpty() || Objects.isNull(grade)) {
                log.warn("Line {}: Missing required data. Skipping.", lineNumber);
                return Optional.empty();
            }

            return Optional.of(new CsvGradeData(studentCode, courseCode, grade));
        } catch (NumberFormatException e) {
            log.warn("Line {}: Grade value '{}' is not a valid number. Skipping. Error: {}", lineNumber, parts[2].trim(), e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            log.warn("Line {}: Unexpected parsing error. Skipping. Error: {}", lineNumber, e.getMessage());
            return Optional.empty();
        }
    }
}
