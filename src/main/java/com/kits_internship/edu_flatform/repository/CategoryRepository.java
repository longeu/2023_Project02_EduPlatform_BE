package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.entity.StatusName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<CategoryEntity, Long> {
    @Query(value = "select c from CategoryEntity c " +
            "where " +
            "   (coalesce(:name) is null or :name = '' or" +
            "   lower(c.name) like concat('%', concat(lower(:name), '%')))" +
            " and (coalesce(:status,null) is null or c.status in :status ) "
    )
    Page<CategoryEntity> filter(StatusName status, String name, Pageable pageable);
}
