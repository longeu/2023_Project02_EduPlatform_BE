package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.CategoryFilterRequest;
import com.kits_internship.edu_flatform.model.request.CategoryRequest;
import com.kits_internship.edu_flatform.model.response.CategoryResponse;
import com.kits_internship.edu_flatform.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/list")
    private ListResponseModel listCategory(CategoryFilterRequest categoryFilter){
        ListResponseModel categoryResponse = categoryService.filter(categoryFilter);
        return categoryResponse;
    }

    @GetMapping("/{id}")
    private CategoryResponse addCategory(@PathVariable Long id) {
        CategoryEntity categoryEntity = categoryService.findById(id);
        CategoryResponse categoryResponse = modelMapper.map(categoryEntity, CategoryResponse.class);
        return categoryResponse;
    }

    @PostMapping("/add")
    private CategoryResponse addCategory(@RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.addCategory(request);
        return categoryResponse;
    }

    @PutMapping("/update/{id}")
    private CategoryResponse updateCategory(@RequestBody CategoryRequest request, @PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.updateCategory(id, request);
        return categoryResponse;
    }
}
