package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.PaymentEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends BaseRepository<PaymentEntity,Long> {
}
