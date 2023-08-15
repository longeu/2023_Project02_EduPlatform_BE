package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.TransactionEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<TransactionEntity,Long> {
}
