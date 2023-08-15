package com.kits_internship.edu_flatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T,E> extends JpaRepository<T,E> {
}
