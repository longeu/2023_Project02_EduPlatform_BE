package com.kits_internship.edu_flatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, E> extends JpaRepository<T, E> {
//    @Query(value = "SELECT t FROM #{#entityName} t WHERE t.id =:id and t.teacherID=:teacherID")
//    T findEntityByTeacherID(Long id, Long teacherID);
//
//    @Query(value = "SELECT t FROM #{#entityName} t WHERE t.id =:id and t.studentID=:studentID")
//    T findEntityByStudentID(Long id, Long studentID);
}
