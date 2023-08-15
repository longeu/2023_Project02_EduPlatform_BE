package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.NotificationEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends BaseRepository<NotificationEntity,Long> {
}
