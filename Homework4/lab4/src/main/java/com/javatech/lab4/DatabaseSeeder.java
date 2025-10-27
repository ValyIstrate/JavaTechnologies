package com.javatech.lab4;

import com.github.javafaker.Faker;
import com.javatech.lab4.persistence.entities.Course;
import com.javatech.lab4.persistence.entities.Instructor;
import com.javatech.lab4.persistence.entities.Pack;
import com.javatech.lab4.persistence.entities.Student;
import com.javatech.lab4.persistence.repositories.CourseRepository;
import com.javatech.lab4.persistence.repositories.InstructorRepository;
import com.javatech.lab4.persistence.repositories.PackRepository;
import com.javatech.lab4.persistence.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final StudentRepository studentRepo;
    private final InstructorRepository instructorRepo;
    private final PackRepository packRepo;
    private final CourseRepository courseRepo;

    private final Faker faker = new Faker(new Random(2602));

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var existingCourses = courseRepo.findAll();
        if (!existingCourses.isEmpty()) {
            return;
        }

        List<Instructor> instructors = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            var fullName = faker.name().fullName();
            Instructor ins = new Instructor();
            ins.setName(fullName);
            ins.setEmail(fullName.toLowerCase().replace(" ", ".") + "@uaic.com");
            instructors.add(ins);
        }
        instructorRepo.saveAll(instructors);

        List<Pack> packs = new ArrayList<>();
        for (int year = 1; year <= 3; year++) {
            for (int sem = 1; sem <= 2; sem++) {
                Pack p = new Pack();
                p.setYear(year);
                p.setSemester(sem);
                p.setName("Year " + year + " Sem " + sem + " Optionals");
                packs.add(p);
            }
        }
        packRepo.saveAll(packs);

        List<Course> courses = new ArrayList<>();
        for (int y = 1; y <= 3; y++) {
            for (int i = 0; i < 3; i++) {
                Course c = new Course();
                var name = faker.educator().course();
                c.setType("COMPULSORY");
                c.setCode("C" + y + "_" + i);
                c.setName(name);
                c.setInstructor(instructors.get(faker.number().numberBetween(0, instructors.size())));
                c.setDescription(faker.lorem().sentence());
                c.setAbbr(name.substring(0, 4));
                c.setGroupCount(0);
                courses.add(c);
            }

            for (int sem = 1; sem <= 2; sem++) {
                int finalSem = sem;
                int finalY = y;
                Pack pack = packs.stream().filter(p -> p.getYear() == finalY && p.getSemester() == finalSem).findFirst().get();
                for (int i = 0; i < 4; i++) {
                    Course c = new Course();
                    var name = faker.educator().course();
                    c.setType("OPTIONAL");
                    c.setCode("O" + y + sem + "_" + i);
                    c.setName(name);
                    c.setPack(pack);
                    c.setInstructor(instructors.get(faker.number().numberBetween(0, instructors.size())));
                    c.setDescription(faker.lorem().sentence());
                    c.setAbbr(name.substring(0, 4));
                    c.setGroupCount(0);
                    courses.add(c);
                }
            }
        }
        courseRepo.saveAll(courses);

        List<Student> students = new ArrayList<>();
        for (int s = 0; s < 10; s++) {
            String fullName = faker.name().fullName();
            Student st = new Student();
            st.setCode("S" + (1000 + s));
            st.setName(fullName);
            st.setEmail(fullName.toLowerCase().replace(" ", ".") + "@student.uaic.com");
            st.setYear(faker.number().numberBetween(1, 4));
            studentRepo.save(st);
            students.add(st);
        }
    }
}
