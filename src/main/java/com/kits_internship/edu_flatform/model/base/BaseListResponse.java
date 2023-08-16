package com.kits_internship.edu_flatform.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseListResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected MetadataResponse metadata;

}
