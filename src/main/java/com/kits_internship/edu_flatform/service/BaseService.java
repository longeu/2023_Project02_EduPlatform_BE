package com.kits_internship.edu_flatform.service;


import com.kits_internship.edu_flatform.security.UserPrinciple;

import java.util.Optional;

public interface BaseService<T> {

    T create(T t);

    T update(Long id, T t);


    boolean delete(Long id);

//    ResultModel findAll(int page, int size, String search);

    T findById(Long id);

}
