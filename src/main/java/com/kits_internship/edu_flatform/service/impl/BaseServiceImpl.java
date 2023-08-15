package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.config.DateConfig;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.repository.BaseRepository;
import com.kits_internship.edu_flatform.service.BaseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseServiceImpl<T, R extends BaseRepository<T, Long>> implements BaseService<T> {

    private final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
    protected final R jpaRepository;

    @Override
    public T create(T t) {
        try {
            t.getClass().getMethod("setCreatedDate", Date.class).invoke(t, new Timestamp(System.currentTimeMillis()));
            t.getClass().getMethod("setModifiedDate", Date.class).invoke(t, new Timestamp(System.currentTimeMillis()));
            T result = jpaRepository.save(t);
            logger.info("id: {}", result.getClass().getMethod("getId").invoke(result));
            return result;
        } catch (Exception e) {
            logger.error("Error creating: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public T update(Long id, T t) {
        try {
            T result = findById(id);
            t.getClass().getMethod("setModifiedDate", Date.class).invoke(t,
                    result.getClass().getMethod("getCreatedDate").invoke(result));
            t.getClass().getMethod("setModifiedDate", Date.class).invoke(t, new Timestamp(System.currentTimeMillis()));
            logger.info("id: {}", t.getClass().getMethod("getCreatedDate").invoke(t));
            return jpaRepository.save(t);
        } catch (Exception e) {
            logger.error("Error updating: {}", e.getMessage());
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Optional<T> result = jpaRepository.existsById(id) ? jpaRepository.findById(id) : Optional.empty();
            if (result.isPresent()) {
                jpaRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error deleting: {}", e.getMessage());
            return false;
        }
    }


    @Override
    public T findById(Long id) {
        Map<String, Object> errors = new HashMap<>();
        try {
            Optional<T> result = jpaRepository.findById(id);
            return result.orElse(null);
        } catch (Exception e) {
            errors.put("errors", e.getMessage());
            throw new NotFoundException(errors);
        }
    }

}
