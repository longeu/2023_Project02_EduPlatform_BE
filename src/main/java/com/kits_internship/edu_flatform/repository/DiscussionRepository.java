package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.DiscussionEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionRepository extends BaseRepository<DiscussionEntity,Long> {
}
