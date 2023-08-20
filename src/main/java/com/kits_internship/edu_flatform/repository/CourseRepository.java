package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.entity.StatusName;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends BaseRepository<CourseEntity, Long> {
    @Query(value = "select c from CourseEntity c " +
            "where (coalesce(:categoryID,null) is null or c.category.id =:categoryID)" +
            "   and (coalesce(:teacherID,null) is null or c.teacher.id =:teacherID)" +
            "   and (coalesce(:studentID,null) is null or c.enrolledStudents =:studentID)" +
            "   and (coalesce(:keyword) is null or :keyword = '' or" +
            "   lower(c.name) like concat('%', concat(lower(:keyword), '%')))" +
            "   and (coalesce(:status,null) is null or c.status in :status ) "
    )
    Page<CourseEntity> filter(
            @Param("status") StatusName status,
            @Param("keyword") String keyword,
            @Param("categoryID") Long categoryID,
            @Param("teacherID") Long teacherID,
            @Param("studentID") Long studentID,
            Pageable pageable
    );

    @Query(value = "SELECT t FROM CourseEntity t WHERE t.id =:id and t.teacher.id=:teacherID")
    Optional<CourseEntity> findEntityByTeacherID(Long id, Long teacherID);
}
