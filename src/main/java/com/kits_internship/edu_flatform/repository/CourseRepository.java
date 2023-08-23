package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.entity.StatusName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends BaseRepository<CourseEntity, Long> {
    @Query(nativeQuery = true, value = "select c.* from courses c" +
            "   join category cat on c.categoryID = cat.id " +
            "   join teacher tea on c.teacherID = tea.id " +
            "   where " +
            "       (coalesce(:categoryID,null) is null or cat.id =:categoryID)" +
            "       and (coalesce(:teacherID,null) is null or tea.id =:teacherID)" +
            "       and (coalesce(:registed,null) is null or " +
            "       IF(:registed = true, :studentID IN (SELECT s.studentID FROM student_courses s WHERE s.courseID = c.id), " +
            "                            :studentID NOT IN (SELECT s.studentID FROM student_courses s WHERE s.courseID = c.id))) " +
            "       and (coalesce(:keyword) is null or :keyword = '' or" +
            "       lower(c.name) like concat('%', concat(lower(:keyword), '%')))" +
            "       and (coalesce(:status,null) is null or c.status = :status ) " +
            "       and (coalesce(:fromDate,null) is null or c.createdDate >= :fromDate ) " +
            "       and (coalesce(:toDate,null) is null or c.createdDate <= :toDate )"
    )
    Page<CourseEntity> filter(
            @Param("status") StatusName status,
            @Param("keyword") String keyword,
            @Param("categoryID") Long categoryID,
            @Param("teacherID") Long teacherID,
            @Param("studentID") Long studentID,
            @Param("registed") Boolean registed,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            Pageable pageable
    );

    @Query(value = "SELECT t FROM CourseEntity t WHERE t.id =:id and t.teacher.id=:teacherID")
    Optional<CourseEntity> findEntityByTeacherID(Long id, Long teacherID);

    @Query(value = "SELECT t FROM CourseEntity t WHERE t.id in :courseIDs")
    List<CourseEntity> findByListIds(@Param("courseIDs") List<Long> courseIDs);
}
