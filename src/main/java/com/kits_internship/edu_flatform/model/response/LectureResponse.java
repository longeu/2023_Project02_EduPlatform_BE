package com.kits_internship.edu_flatform.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kits_internship.edu_flatform.entity.StatusName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LectureResponse {
    private Long id;
    private String description;
    private String name;
    private String documentPath;
    private String videoPath;
    private Long courseID;
    private StatusName status;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDate;
}
