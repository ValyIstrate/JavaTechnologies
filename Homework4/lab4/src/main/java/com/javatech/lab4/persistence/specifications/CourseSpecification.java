package com.javatech.lab4.persistence.specifications;

import com.javatech.lab4.persistence.entities.Course;
import com.javatech.lab4.web.requests.FilterCoursesRequests;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseSpecification {

    public CourseSpecification() {
    }

    public static Specification<Course> filterCourses(FilterCoursesRequests filter) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(filter.getPackId())) {
                var nonNullPredicate = cb.isNotNull(root.get("pack"));
                var idEqualityPredicate = cb.equal(root.get("pack").get("id"), filter.getPackId());
                var packPredicate = cb.and(nonNullPredicate, idEqualityPredicate);
                predicates.add(packPredicate);
            }

            if (Objects.nonNull(filter.getInstructorId())) {
                predicates.add(cb.equal(root.get("instructor").get("id"), filter.getInstructorId()));
            }

            if (Objects.nonNull(filter.getNameLike())) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + filter.getNameLike() + "%"
                        )
                );
            }

            if (Objects.nonNull(filter.getYear())) {
                predicates.add(cb.equal(root.get("year"), filter.getYear()));
            }

            if (Objects.nonNull(filter.getSemester())) {
                predicates.add(cb.equal(root.get("semester"), filter.getSemester()));
            }

            if (Objects.nonNull(filter.getType())) {
                predicates.add(cb.equal(root.get("type"), filter.getType()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
