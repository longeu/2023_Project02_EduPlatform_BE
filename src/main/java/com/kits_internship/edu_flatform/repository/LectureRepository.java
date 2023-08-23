package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.entity.LectureEntity;
import com.kits_internship.edu_flatform.entity.StatusName;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends BaseRepository<LectureEntity, Long> {
    @Query(nativeQuery = true, value = "select l.* from lectures l" +
            "   join courses c on c.id = l.courseID " +
            "   where " +
            "       l.courseID = :courseID" +
            "       and (coalesce(:keyword) is null or :keyword = '' or" +
            "       lower(l.name) like concat('%', concat(lower(:keyword), '%')))" +
            "       and (coalesce(:status,null) is null or l.status = :status ) "
    )
    Page<LectureEntity> filter(
            @Param("status") StatusName status,
            @Param("keyword") String keyword,
            @Param("courseID") Long courseID,
            Pageable pageable
    );

    @Query(value = "SELECT l FROM LectureEntity l WHERE l.id =:id and l.course.id=:courseID")
    Optional<LectureEntity> findByIdAndCourse(Long id, Long courseID);

}
