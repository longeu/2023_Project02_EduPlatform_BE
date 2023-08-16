package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.entity.OtpEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends BaseRepository<OtpEntity,Long> {
    Optional<OtpEntity> findByEmail(String email);
}
