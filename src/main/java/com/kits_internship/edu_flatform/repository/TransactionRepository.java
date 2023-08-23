package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.PaymentEntity;
import com.kits_internship.edu_flatform.entity.StatusName;
import com.kits_internship.edu_flatform.entity.TransactionEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends BaseRepository<TransactionEntity, Long> {
    @Query(value = "select c from TransactionEntity c " +
            " where " +
            "   (coalesce(:keyword) is null or :keyword = '' or" +
            "   lower(c.transactionName) like concat('%', concat(lower(:keyword), '%')))" +
            "   and (coalesce(:studentID,null) is null or c.student.id =:studentID)"
    )
    Page<TransactionEntity> filter(
            @Param("keyword") String keyword,
            @Param("studentID") Long studentID,
            Pageable pageable
    );

    @Query(nativeQuery = true, value = "select * from transaction t where t.id =:id and t.studentID =:studentID")
    Optional<TransactionEntity> findByIdAndUser(Long id, Long studentID);


    @Query(nativeQuery = true, value = "select * from transaction t where t.studentID =:studentID")
    List<TransactionEntity> findByStudentID(Long studentID);
}
