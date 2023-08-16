package com.kits_internship.edu_flatform.model.base;

import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ListResponseModel extends BaseListResponse {
    private List<?> results;
}