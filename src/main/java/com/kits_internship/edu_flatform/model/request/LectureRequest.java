package com.kits_internship.edu_flatform.model.request;

import com.kits_internship.edu_flatform.entity.StatusName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LectureRequest {
    private String description;
    @NotBlank
    private String name;
    @NotNull
    private String documentPath;
    @NotBlank
    private String videoPath;
    @NotNull
    private StatusName status;
    @NotNull
    private Long courseID;
}