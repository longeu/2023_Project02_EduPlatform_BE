package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.base.MetadataResponse;
import com.kits_internship.edu_flatform.model.request.CategoryFilterRequest;
import com.kits_internship.edu_flatform.model.request.CategoryRequest;
import com.kits_internship.edu_flatform.model.response.CategoryResponse;
import com.kits_internship.edu_flatform.repository.CategoryRepository;
import com.kits_internship.edu_flatform.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryEntity, CategoryRepository> implements CategoryService {
    public CategoryServiceImpl(CategoryRepository jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryResponse addCategory(CategoryRequest request) {
        CategoryEntity categoryEntity = modelMapper.map(request, CategoryEntity.class);
        categoryEntity = create(categoryEntity);
        CategoryResponse categoryResponse = modelMapper.map(categoryEntity, CategoryResponse.class);

        return categoryResponse;
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        CategoryEntity categoryEntity = modelMapper.map(request, CategoryEntity.class);
        categoryEntity = update(id, categoryEntity);
        CategoryResponse categoryResponse = modelMapper.map(categoryEntity, CategoryResponse.class);
        return categoryResponse;
    }

    @Override
    public ListResponseModel filter(CategoryFilterRequest categoryFilter) {
        Page<CategoryEntity> categoryEntities = categoryRepository.filter(
                categoryFilter.getStatus(),
                categoryFilter.getName(),
                PageRequest.of(categoryFilter.getPage() - 1, categoryFilter.getLimit(), Sort.by(Sort.Order.desc("createdDate"))));

        ListResponseModel responses = new ListResponseModel();
        List<CategoryResponse> responseList = categoryEntities.stream().map(categoryEntity -> modelMapper.map(categoryEntity, CategoryResponse.class)).collect(Collectors.toList());
        responses.setResults(responseList);

        MetadataResponse metadata = new MetadataResponse(
                categoryEntities.getTotalElements(),
                categoryFilter.getPage(),
                categoryFilter.getLimit()
        );
        responses.setMetadata(metadata);
        return responses;
    }
}
