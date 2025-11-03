package com.javatech.lab4.services;

import com.javatech.lab4.persistence.entities.Course;
import com.javatech.lab4.persistence.entities.Student;
import com.javatech.lab4.persistence.entities.StudentCoursePreference;
import com.javatech.lab4.persistence.repositories.CourseRepository;
import com.javatech.lab4.persistence.repositories.StudentCoursePreferenceRepository;
import com.javatech.lab4.persistence.repositories.StudentRepository;
import com.javatech.lab4.persistence.specifications.StudentSpecification;
import com.javatech.lab4.services.dtos.StudentDto;
import com.javatech.lab4.services.exceptions.EntityNotFoundException;
import com.javatech.lab4.services.exceptions.StudentAlreadyExistsException;
import com.javatech.lab4.web.requests.CreateStudentPackPreferencesDto;
import com.javatech.lab4.web.requests.CreateStudentPreferencesRequest;
import com.javatech.lab4.web.requests.CreateStudentRequest;
import com.javatech.lab4.web.requests.FilterStudentsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentCoursePreferenceRepository studentCoursePreferenceRepository;
    private final CourseRepository courseRepository;

    public StudentDto createStudent(CreateStudentRequest createStudentRequest) {
        studentRepository
                .findByEmailOrCode(createStudentRequest.email(), createStudentRequest.code())
                .ifPresent(student -> {throw new StudentAlreadyExistsException("Student with provided email or code already exists");});

        Student newStudent = Student.builder()
                .code(createStudentRequest.code())
                .year(createStudentRequest.year())
                .name(createStudentRequest.name())
                .email(createStudentRequest.email())
                .build();

        return StudentDto.fromEntity(studentRepository.save(newStudent));
    }

    public Page<StudentDto> filterStudents(FilterStudentsRequest filter, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return studentRepository.findAll(StudentSpecification.filterStudents(filter), pageable)
                .map(StudentDto::fromEntity);
    }

    public StudentDto getStudent(Long id) {
        return StudentDto.fromEntity(getById(id));
    }

    public StudentDto addStudentPreferences(Long id, CreateStudentPreferencesRequest createStudentPreferencesRequest) {
        var student = getById(id);

        var courseIds = createStudentPreferencesRequest.packs().stream()
                .map(CreateStudentPackPreferencesDto::courses)
                .flatMap(Collection::stream)
                .toList();

        var courses = courseRepository.findAllById(courseIds)
                .stream()
                .collect(Collectors.toMap(
                        Course::getId,
                        Function.identity()
                ));

        var newPreferences = createStudentPreferencesRequest.packs().stream()
                .map(pack ->
                        mapToStudentCoursePreferencesForPack(pack, courses, student))
                .flatMap(Collection::stream)
                .toList();

        student.setCoursePreferences(newPreferences);
        var savedStudent = studentRepository.save(student);

        return StudentDto.fromEntity(savedStudent);
    }

    private List<StudentCoursePreference> mapToStudentCoursePreferencesForPack(CreateStudentPackPreferencesDto dto,
                                                                               Map<Long, Course> courses,
                                                                               Student student) {
        return IntStream.range(0, dto.courses().size())
                .mapToObj(index -> {
                    Long courseId = dto.courses().get(index);
                    return StudentCoursePreference.builder()
                            .course(courses.get(courseId))
                            .student(student)
                            .rank(index)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with provided id not found!"));
    }
}
