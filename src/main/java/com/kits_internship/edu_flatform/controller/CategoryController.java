package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.CategoryFilterRequest;
import com.kits_internship.edu_flatform.model.request.CategoryRequest;
import com.kits_internship.edu_flatform.model.response.CategoryResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController extends BaseController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/list")
    private ListResponseModel listCategory(CategoryFilterRequest categoryFilter, Principal currentUser) {
        Map<String, Object> errors = new HashMap<>();
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        ListResponseModel categoryResponse = categoryService.filterByCurrentUser(categoryFilter, user);
        return categoryResponse;
    }

    @GetMapping("/{id}")
    private CategoryResponse getById(@PathVariable Long id, Principal currentUser) {
        Map<String, Object> errors = new HashMap<>();
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        Optional<CategoryEntity> categoryEntity = categoryService.findCategoryId(id);
        if(categoryEntity.isEmpty()){
            errors.put("category", "Not found category");
            throw new NotFoundException(errors);
        }
        CategoryResponse categoryResponse = modelMapper.map(categoryEntity, CategoryResponse.class);
        return categoryResponse;
    }

    @PostMapping("/add")
    private CategoryResponse addCategory(@RequestBody CategoryRequest request, Principal currentUser) {
        Map<String, Object> errors = new HashMap<>();
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return categoryService.addByCurrentUser(request, user);
    }

    @PutMapping("/update/{id}")
    private CategoryResponse updateCategory(@RequestBody CategoryRequest request, @PathVariable Long id, Principal currentUser) {
        Map<String, Object> errors = new HashMap<>();
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return categoryService.updateByCurrentUser(id, request, user);
    }
}
