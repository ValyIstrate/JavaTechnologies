package com.javatech.lab4.persistence.specifications;

import com.javatech.lab4.persistence.entities.Student;
import com.javatech.lab4.web.requests.FilterStudentsRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentSpecification {

    private StudentSpecification() {}

    public static Specification<Student> filterStudents(FilterStudentsRequest filter) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(filter.year())) {
                predicates.add(cb.equal(root.get("year"), filter.year()));
            }

            if (Objects.nonNull(filter.search())) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + filter.search() + "%"
                        )
                );

                predicates.add(
                        cb.like(
                                cb.lower(root.get("code")),
                                "%" + filter.search() + "%"
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
