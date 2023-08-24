package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.ResultEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends BaseRepository<ResultEntity, Long> {
    @Query(value = "select r from ResultEntity r " +
            "   join StudentEntity s on s.id = r.student.id " +
            "   join LectureEntity l on l.id = r.lecture.id" +
            "   where " +
            "       r.lecture.id = :lectureID and r.student.id = :studentID")
    Page<ResultEntity> filter(
            @Param("lectureID") Long lectureID,
            @Param("studentID") Long studentID,
            Pageable pageable
    );

    @Query(value = "select r from ResultEntity r where r.id =:id and r.student.id =:studentID")
    Optional<ResultEntity> findByIdAndUser(Long id, Long studentID);
}
