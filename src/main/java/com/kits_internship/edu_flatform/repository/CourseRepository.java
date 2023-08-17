package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.entity.StatusName;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends BaseRepository<CourseEntity,Long> {
    @Query(value = "select c from CourseEntity c " +
            "where " +
            "   (coalesce(:name) is null or :name = '' or" +
            "   lower(c.name) like concat('%', concat(lower(:name), '%')))" +
            " and (coalesce(:status,null) is null or c.status in :status ) "
    )
    Page<CourseEntity> filter(StatusName status, String name, Pageable pageable);

    @Query(value = "SELECT t FROM CourseEntity t WHERE t.id =:id and t.teacher.id=:teacherID")
    Optional<CourseEntity> findEntityByTeacherID(Long id, Long teacherID);
}
