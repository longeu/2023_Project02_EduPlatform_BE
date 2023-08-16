package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.CategoryFilterRequest;
import com.kits_internship.edu_flatform.model.request.CategoryRequest;
import com.kits_internship.edu_flatform.model.response.CategoryResponse;

public interface CategoryService extends BaseService<CategoryEntity> {

    CategoryResponse addCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    ListResponseModel filter(CategoryFilterRequest categoryFilter);
}
