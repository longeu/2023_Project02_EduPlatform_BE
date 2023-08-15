package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);

}
