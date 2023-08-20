package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.CategoryFilterRequest;
import com.kits_internship.edu_flatform.model.request.CategoryRequest;
import com.kits_internship.edu_flatform.model.response.CategoryResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;

import java.util.Optional;

public interface CategoryService extends BaseService<CategoryEntity> {

    CategoryResponse addByCurrentUser(CategoryRequest request, Optional<UserPrinciple> user);

    CategoryResponse updateByCurrentUser(Long id, CategoryRequest request, Optional<UserPrinciple> user);

    ListResponseModel filterByCurrentUser(CategoryFilterRequest categoryFilter, Optional<UserPrinciple> user);

    Optional<CategoryEntity> findCategoryId(Long id);
}
