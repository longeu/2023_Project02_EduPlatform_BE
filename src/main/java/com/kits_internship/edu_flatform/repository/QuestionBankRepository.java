package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.QuestionBankEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionBankRepository extends BaseRepository<QuestionBankEntity, Long> {
    @Query(nativeQuery = true, value = "select qb.* from question_bank qb" +
            "   join lectures l on c.id = qb.lectureID " +
            "   where " +
            "       l.id = :lectureID" +
            "       and (coalesce(:keyword) is null or :keyword = '' or" +
            "       lower(l.name) like concat('%', concat(lower(:keyword), '%')))"
    )
    Page<QuestionBankEntity> filter(
            @Param("keyword") String keyword,
            @Param("lectureID") Long lectureID,
            Pageable pageable
    );

    @Query(value = "SELECT qb FROM QuestionBankEntity qb WHERE qb.id =:id and qb.lecture.id=:lectureID")
    Optional<QuestionBankEntity> findByIdAndLectureID(Long id, Long lectureID);
}
