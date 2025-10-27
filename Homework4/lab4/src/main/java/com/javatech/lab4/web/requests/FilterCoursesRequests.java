package com.javatech.lab4.web.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FilterCoursesRequests {
    Long packId;
    Long instructorId;
    String type;
    Long year;
    Long semester;
    String nameLike;
}
