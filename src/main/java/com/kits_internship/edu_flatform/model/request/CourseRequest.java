package com.kits_internship.edu_flatform.model.request;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.entity.StatusName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CourseRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String objectives;
    @NotBlank
    private String target;
    @NotBlank
    private String image;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Long categoryID;

}
