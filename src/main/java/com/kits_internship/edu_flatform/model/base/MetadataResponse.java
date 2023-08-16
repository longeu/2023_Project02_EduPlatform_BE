package com.kits_internship.edu_flatform.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MetadataResponse {
    private long total;
    private int page;
    private int limit;

}
