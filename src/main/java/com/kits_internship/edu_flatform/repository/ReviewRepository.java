package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.ReviewEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends BaseRepository<ReviewEntity,Long> {
}
