package com.kits_internship.edu_flatform.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kits_internship.edu_flatform.entity.StatusName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CourseResponse {
    private Long id;
    private String name;
    private String description;
    private String objectives;
    private String target;
    private String image;
    private StatusName status;
    private BigDecimal price;
    private TeacherResponse teacher;
    private CategoryResponse category;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDate;
}
